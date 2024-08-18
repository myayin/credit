package com.colendi.credit.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CreditDto {

    private BigDecimal creditAmount;

    private String currency;

    private String status;

    private Integer totalInstallmentCount;

    private Long remainingInstallmentCount;

    private Instant createdDate;

}
