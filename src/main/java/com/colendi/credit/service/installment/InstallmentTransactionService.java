package com.colendi.credit.service.installment;

import com.colendi.credit.model.Credit;
import com.colendi.credit.model.Installment;
import com.colendi.credit.model.Payment;

public interface InstallmentTransactionService {

    void save(Payment payment, Credit credit,
              Installment installment);
}
