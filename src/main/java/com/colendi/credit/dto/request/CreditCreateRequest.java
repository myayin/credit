package com.colendi.credit.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CreditCreateRequest {

    private Long userId;
    private BigDecimal creditAmount;
    private String currency;
    private String creditType;
    private Integer installmentCount;
}
