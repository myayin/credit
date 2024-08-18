package com.colendi.credit.service.impl.credit;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.model.Credit;
import com.colendi.credit.model.Installment;
import com.colendi.credit.model.enums.BasicStatusEnum;
import com.colendi.credit.repository.CreditRepository;
import com.colendi.credit.service.credit.CreditTransactionService;
import com.colendi.credit.service.installment.InstallmentService;
import java.math.BigDecimal;
import java.util.stream.IntStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditTransactionServiceImpl implements CreditTransactionService {
    private final CreditRepository creditRepository;
    private final InstallmentService installmentService;

    @Transactional
    public List<InstallmentDto> saveCreditAndInstallments(final Credit credit, final Integer installmentCount) {
        creditRepository.save(credit);
        List<Installment> savedInstallments = saveInstallments(credit, installmentCount);
        return installmentService.getInstallmentDtos(savedInstallments);
    }

    private List<Installment> saveInstallments(final Credit credit, final Integer installmentCount) {
        List<Installment> installments = createInstallments(credit, installmentCount);
        return installmentService.saveAll(installments);
    }

    private List<Installment> createInstallments(final Credit credit, final Integer numberOfInstallment) {
        BigDecimal installmentAmount = installmentService.calculateInstallmentAmount(credit, numberOfInstallment);
        return generateInstallments(credit, numberOfInstallment, installmentAmount);
    }

    private List<Installment> generateInstallments(final Credit credit, final Integer numberOfInstallment, final BigDecimal installmentAmount) {
        return IntStream.range(1, numberOfInstallment + 1).mapToObj(i -> createInstallment(credit, installmentAmount, i)).collect(
                Collectors.toList());
    }
    private Installment createInstallment(final Credit credit, final BigDecimal amount, final int installmentNumber) {
        Installment installment = new Installment();
        installment.setCredit(credit);
        installment.setAmount(amount);
        installment.setPaidAmount(BigDecimal.ZERO);
        installment.setStatus(BasicStatusEnum.ACTIVE.name());
        installment.setCurrency(credit.getCurrency());
        installment.setCreatedDate(new Date().toInstant());
        installment.setAccumulatedLateFee(BigDecimal.ZERO);
        installment.setDueDate(installmentService.calculateDueDate(credit.getCreatedDate(), installmentNumber));
        return installment;
    }

}
