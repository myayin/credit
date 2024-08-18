package com.colendi.credit.controller;

import static com.colendi.credit.validation.InstallmentRequestValidator.validateInstallmentPayment;

import com.colendi.credit.dto.request.InstallmentPaymentRequest;
import com.colendi.credit.dto.response.InstallmentPaymentResponse;
import com.colendi.credit.dto.response.Result;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.service.installment.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v0/installment", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @PostMapping("/payment")
    public ResponseEntity<InstallmentPaymentResponse> payment(@RequestBody InstallmentPaymentRequest request) throws ColendiException {
        validateInstallmentPayment(request);
        InstallmentPaymentResponse response = installmentService.pay(request);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }
}
