package com.colendi.credit.service.impl.installment;

import static com.colendi.credit.constants.CreditConstants.LATE_PAYMENT_INTEREST_RATE;
import static com.colendi.credit.constants.CreditConstants.calculateInstallmentDueDateValue;
import static com.colendi.credit.constants.CreditResponseCodes.E_INSTALLMENT_NOT_FOUND;
import static com.colendi.credit.constants.CreditResponseCodes.E_INTEREST_CALCULATION_IN_PROGRESS;
import static com.colendi.credit.constants.CreditResponseCodes.E_INVALID_AMOUNT;
import static com.colendi.credit.model.enums.BasicStatusEnum.ACTIVE;
import static com.colendi.credit.model.enums.BasicStatusEnum.PASSIVE;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.dto.request.InstallmentPaymentRequest;
import com.colendi.credit.dto.response.InstallmentPaymentResponse;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.mapper.InstallmentMapper;
import com.colendi.credit.model.Credit;
import com.colendi.credit.model.Installment;
import com.colendi.credit.model.Payment;
import com.colendi.credit.model.enums.BasicStatusEnum;
import com.colendi.credit.repository.InstallmentRepository;
import com.colendi.credit.service.ConfigService;
import com.colendi.credit.service.PaymentService;
import com.colendi.credit.service.installment.InstallmentService;
import com.colendi.credit.service.installment.InstallmentTransactionService;
import com.colendi.credit.util.DateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    private final PaymentService paymentService;

    private final InstallmentTransactionService installmentTransactionService;

    private final ConfigService configService;

    @Override
    public BigDecimal calculateInstallmentAmount(Credit credit, Integer numberOfInstallment) {
        return credit.getCreditAmount().divide(BigDecimal.valueOf(numberOfInstallment), 2, RoundingMode.HALF_UP);
    }

    @Override
    public List<Installment> saveAll(List<Installment> installments) {
        return installmentRepository.saveAll(installments);
    }

    @Override
    public List<InstallmentDto> getInstallmentDtos(List<Installment> installments) {
        List<InstallmentDto> installmentDtos = new ArrayList<>();
        installments.forEach(installment -> installmentDtos.add(InstallmentMapper.toDto(installment)));
        return installmentDtos;
    }

    @Override
    public InstallmentPaymentResponse pay(InstallmentPaymentRequest request) throws ColendiException {
        Installment installment = findByIdAndStatus(request.getInstallmentId(), ACTIVE);
        validateInstalment(request, installment);
        processPayment(request, installment);
        Credit credit = installment.getCredit();
        updateCreditStatusIfNeeded(credit);
        Payment payment = paymentService.getPayment(request, installment);
        installmentTransactionService.save(payment, credit, installment);
        return new InstallmentPaymentResponse();
    }

    private Installment findByIdAndStatus(Long id, BasicStatusEnum basicStatusEnum) throws ColendiException {
        return installmentRepository.findByIdAndStatus(id, basicStatusEnum.name()).orElseThrow(() -> new ColendiException(E_INSTALLMENT_NOT_FOUND.name(), E_INSTALLMENT_NOT_FOUND.getMessage()));
    }

    private void updateCreditStatusIfNeeded(Credit credit) {
        List<Installment> remainingInstallments = credit.getInstallments().stream().filter(i -> ACTIVE.name().equals(i.getStatus())).toList();
        if (remainingInstallments.isEmpty()) {
            credit.setModifiedDate(new Date().toInstant());
            credit.setStatus(PASSIVE.name());
        }
    }

    private void processPayment(InstallmentPaymentRequest request, Installment installment) throws ColendiException {
        BigDecimal remainingAmount = installment.getRemainingAmount();
        if (remainingAmount.compareTo(request.getAmount()) >= 0) {
            installment.setPaidAmount(installment.getPaidAmount().add(request.getAmount()));
            installment.setModifiedDate(new Date().toInstant());
            if (remainingAmount.compareTo(request.getAmount()) == 0) {
                installment.setStatus(PASSIVE.name());
            }
        } else {
            throw new ColendiException(E_INVALID_AMOUNT.name(), E_INVALID_AMOUNT.getMessage());
        }
    }

    private void validateInstalment(InstallmentPaymentRequest request, Installment installment) throws ColendiException {
        if (!request.getCreditId().equals(installment.getCredit().getId()) || !request.getUserId().equals(installment.getCredit().getUser().getId()) || !request.getCurrency().equals(installment.getCurrency())) {
            throw new ColendiException(E_INSTALLMENT_NOT_FOUND.name(), E_INSTALLMENT_NOT_FOUND.getMessage());
        }
        if (installment.getDueDate().isBefore(DateUtil.resetTime(new Date())) && installment.getLateFeeCalculatedDate().isBefore(DateUtil.resetTime(new Date()))) {
            throw new ColendiException(E_INTEREST_CALCULATION_IN_PROGRESS.name(), E_INTEREST_CALCULATION_IN_PROGRESS.getMessage());
        }
    }

    public Instant calculateDueDate(Instant startInstant, int installmentNumber) {
        LocalDate startDate = LocalDate.ofInstant(startInstant, ZoneId.systemDefault());
        LocalDate dueDate = startDate.plusDays(calculateInstallmentDueDateValue * installmentNumber);

        dueDate = adjustForWeekends(dueDate);

        return dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    private LocalDate adjustForWeekends(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return date.plusDays(2);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return date.plusDays(1);
        }
        return date;
    }

    public List<InstallmentDto> saveLateFee(Pageable pageable) throws ColendiException {
        List<Installment> installments = installmentRepository.findByDueDateBeforeAndStatusAndLateFeeCalculatedDateBefore(DateUtil.resetTime(new Date()), ACTIVE.name(), DateUtil.resetTime(new Date()), pageable);
        BigDecimal interestRate = configService.findByKey(LATE_PAYMENT_INTEREST_RATE).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        final List<Installment> installmentList = new ArrayList<>();
        installments.forEach(installment -> {
            long delinquentDays = ChronoUnit.DAYS.between(installment.getDueDate(), new Date().toInstant());
            BigDecimal lateFee = BigDecimal.valueOf(delinquentDays).multiply(interestRate).multiply(installment.getAmount()).divide(BigDecimal.valueOf(360), 2, RoundingMode.HALF_UP);
            installment.setAccumulatedLateFee(lateFee);
            installment.setLateFeeCalculatedDate(DateUtil.resetTime(new Date()));
            installmentList.add(installment);
        });
        installmentRepository.saveAll(installmentList);
        return getInstallmentDtos(installments);
    }
}
