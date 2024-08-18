package com.colendi.credit.job;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.service.ConfigService;
import com.colendi.credit.service.installment.InstallmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.colendi.credit.constants.CreditConstants.JOB_FETCH_SIZE;

@Component
@AllArgsConstructor
public class CalculateLateFeeJobService {

    private final InstallmentService installmentService;
    private final ConfigService configService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateLateFeeJob() throws ColendiException {
        BigDecimal fetchSize = configService.findByKey(JOB_FETCH_SIZE);
        List<InstallmentDto> installmentDtos;
        int pageNumber = 0;
        do {
            Pageable pageable = PageRequest.of(pageNumber, fetchSize.intValue());
            installmentDtos = installmentService.saveLateFee(pageable);

            if (!installmentDtos.isEmpty()) {
                pageNumber++;
            }
        } while (!installmentDtos.isEmpty());
    }
}
