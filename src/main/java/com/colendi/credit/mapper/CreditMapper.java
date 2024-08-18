package com.colendi.credit.mapper;

import com.colendi.credit.dto.CreditDto;
import com.colendi.credit.model.Credit;

import java.util.List;
import java.util.Objects;

import com.colendi.credit.model.Installment;
import com.colendi.credit.model.enums.BasicStatusEnum;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreditMapper {

    public static CreditDto toDto(Credit credit) {
        if (Objects.isNull(credit)) {
            return null;
        }
        CreditDto creditDto = new CreditDto();
        creditDto.setId(credit.getId());
        creditDto.setCreditAmount(credit.getCreditAmount());
        creditDto.setCurrency(credit.getCurrency());
        creditDto.setStatus(credit.getStatus());
        creditDto.setCreatedDate(credit.getCreatedDate());
        List<Installment> installments = credit.getInstallments();
        long remainingInstallmentCount = installments.stream().filter(i -> BasicStatusEnum.ACTIVE.name().equals(i.getStatus())).count();
        creditDto.setRemainingInstallmentCount(remainingInstallmentCount);
        creditDto.setTotalInstallmentCount(installments.size());
        return creditDto;
    }

    public static Credit toEntity(CreditDto creditDto) {
        if (Objects.isNull(creditDto)) {
            return null;
        }
        Credit credit = new Credit();
        creditDto.setCreditAmount(creditDto.getCreditAmount());
        creditDto.setCurrency(creditDto.getCurrency());
        creditDto.setStatus(creditDto.getStatus());
        creditDto.setCreatedDate(creditDto.getCreatedDate());
        return credit;
    }
}
