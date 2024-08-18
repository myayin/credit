package com.colendi.credit.validation;


import com.colendi.credit.constants.CreditResponseCodes;
import com.colendi.credit.dto.request.InstallmentPaymentRequest;
import com.colendi.credit.exception.ColendiException;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InstallmentRequestValidator {

    public static void validateInstallmentPayment(InstallmentPaymentRequest request) throws ColendiException {
        if (Objects.isNull(request.getInstallmentId())) {
            throw new ColendiException(
                    CreditResponseCodes.E_INVALID_INSTALLMENT_ID.name(),
                    CreditResponseCodes.E_INVALID_INSTALLMENT_ID.getMessage());
        }
        if (Objects.isNull(request.getUserId())) {
            throw new ColendiException(
                    CreditResponseCodes.E_INVALID_USER_ID.name(),
                    CreditResponseCodes.E_INVALID_USER_ID.getMessage());
        }
        if (Objects.isNull(request.getCreditId())) {
            throw new ColendiException(
                    CreditResponseCodes.E_INVALID_CREDIT_ID.name(),
                    CreditResponseCodes.E_INVALID_CREDIT_ID.getMessage());
        }
        if (Objects.isNull(request.getCurrency()) || request.getCurrency().isEmpty()) {
            throw new ColendiException(
                    CreditResponseCodes.E_INVALID_CURRENCY.name(),
                    CreditResponseCodes.E_INVALID_CURRENCY.getMessage());
        }
        if (Objects.isNull(request.getAmount())) {
            throw new ColendiException(
                    CreditResponseCodes.E_INVALID_AMOUNT.name(),
                    CreditResponseCodes.E_INVALID_AMOUNT.getMessage());
        }
    }
}
