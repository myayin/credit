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
public class InstallmentPaymentRequest {
private Long installmentId;
private Long userId;
private Long creditId;
private String currency;
private BigDecimal amount;
}
