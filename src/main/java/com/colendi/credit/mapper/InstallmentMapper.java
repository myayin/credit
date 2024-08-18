package com.colendi.credit.mapper;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.model.Installment;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InstallmentMapper {
    public static InstallmentDto toDto(Installment installment) {
        if (Objects.isNull(installment)) {
            return null;
        }
        InstallmentDto installmentDto = new InstallmentDto();
        installmentDto.setAmount(installment.getAmount());
        installmentDto.setDueDate(installment.getDueDate());
        installmentDto.setCurrency(installment.getCurrency());
        installmentDto.setId(installment.getId());
        return installmentDto;
    }
}
