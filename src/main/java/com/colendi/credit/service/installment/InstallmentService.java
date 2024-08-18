package com.colendi.credit.service.installment;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.dto.request.InstallmentPaymentRequest;
import com.colendi.credit.dto.response.InstallmentPaymentResponse;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.model.Credit;
import com.colendi.credit.model.Installment;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface InstallmentService {

    BigDecimal calculateInstallmentAmount(Credit credit, Integer numberOfInstallment);

    List<Installment> saveAll(List<Installment> installments);

    List<InstallmentDto> getInstallmentDtos(List<Installment> installments);

    Instant calculateDueDate(Instant startInstant, int installmentNumber);

    InstallmentPaymentResponse pay(InstallmentPaymentRequest request) throws ColendiException;

    List<InstallmentDto> saveLateFee(Pageable pageable) throws ColendiException;

}
