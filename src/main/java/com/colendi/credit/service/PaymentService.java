package com.colendi.credit.service;

import com.colendi.credit.dto.request.InstallmentPaymentRequest;
import com.colendi.credit.model.Installment;
import com.colendi.credit.model.Payment;

public interface PaymentService {
   Payment getPayment(InstallmentPaymentRequest request, Installment installment) ;
}
