package com.colendi.credit.service.impl.installment;

import com.colendi.credit.model.Credit;
import com.colendi.credit.model.Installment;
import com.colendi.credit.model.Payment;
import com.colendi.credit.repository.CreditRepository;
import com.colendi.credit.repository.InstallmentRepository;
import com.colendi.credit.repository.PaymentRepository;
import com.colendi.credit.service.installment.InstallmentTransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstallmentTransactionServiceImpl implements InstallmentTransactionService {

    private final CreditRepository creditRepository;
    private final InstallmentRepository installmentRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void save(Payment payment, Credit credit,
                     Installment installment) {
        creditRepository.save(credit);
        paymentRepository.save(payment);
        installmentRepository.save(installment);
    }
}
