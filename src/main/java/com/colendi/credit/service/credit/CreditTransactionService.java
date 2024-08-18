package com.colendi.credit.service.credit;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.model.Credit;
import java.util.List;

public interface CreditTransactionService {

    List<InstallmentDto> saveCreditAndInstallments(final Credit credit, final Integer installmentCount);
}
