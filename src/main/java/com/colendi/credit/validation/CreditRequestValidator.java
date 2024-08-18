package com.colendi.credit.validation;

import com.colendi.credit.dto.request.CreditCreateRequest;
import com.colendi.credit.exception.ColendiException;

import java.util.Objects;

import com.colendi.credit.model.enums.CreditTypeEnum;
import lombok.experimental.UtilityClass;

import static com.colendi.credit.constants.CreditResponseCodes.*;

@UtilityClass
public class CreditRequestValidator {

    public static void validateCreateCredit(CreditCreateRequest request) throws ColendiException {
        if (Objects.isNull(request.getCreditAmount())) {
            throw new ColendiException(E_INVALID_AMOUNT.name(), E_INVALID_AMOUNT.getMessage());
        }
        if (Objects.isNull(request.getCurrency()) || request.getCurrency().isEmpty()) {
            throw new ColendiException(E_INVALID_CURRENCY.name(), E_INVALID_CURRENCY.getMessage());
        }
        if (Objects.isNull(request.getUserId())) {
            throw new ColendiException(E_INVALID_USER_ID.name(), E_INVALID_USER_ID.getMessage());
        }
        if (Objects.isNull(request.getInstallmentCount())) {
            throw new ColendiException(E_INVALID_INSTALLMENT_COUNT.name(), E_INVALID_INSTALLMENT_COUNT.getMessage());
        }
        if (Objects.isNull(request.getCreditType()) || request.getCreditType().isEmpty()
                || !CreditTypeEnum.getCreditTypes().contains(request.getCreditType())) {
            throw new ColendiException(E_INVALID_CREDIT_TYPE.name(), E_INVALID_CREDIT_TYPE.getMessage());
        }
    }
}
