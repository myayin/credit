package com.colendi.credit.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Accessors(chain = true)
public class InstallmentDto {

    private Long id;
    private Instant dueDate;
    private BigDecimal amount;
    private String currency;
}
