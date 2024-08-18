package com.colendi.credit.service.impl;

import com.colendi.credit.dto.request.InstallmentPaymentRequest;
import com.colendi.credit.model.Installment;
import com.colendi.credit.model.Payment;
import com.colendi.credit.service.PaymentService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment getPayment(InstallmentPaymentRequest request, Installment installment) {
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setInstallment(installment);
        payment.setCreatedDate(new Date().toInstant());
        return payment;
    }
}
