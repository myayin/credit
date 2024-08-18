package com.colendi.credit.service.impl.installment;

import static com.colendi.credit.constants.CreditConstants.calculateInstallmentDueDateValue;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.mapper.InstallmentMapper;
import com.colendi.credit.model.Credit;
import com.colendi.credit.model.Installment;
import com.colendi.credit.repository.InstallmentRepository;
import com.colendi.credit.service.installment.InstallmentService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    @Override
    public BigDecimal calculateInstallmentAmount(Credit credit, Integer numberOfInstallment) {
        return credit.getCreditAmount().divide(BigDecimal.valueOf(numberOfInstallment), RoundingMode.HALF_UP);
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

}
