package com.colendi.credit.dto;

import com.colendi.credit.model.Installment;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PaymentDto {

    private String currency;
    private BigDecimal amount;
    private Installment installment;
}
