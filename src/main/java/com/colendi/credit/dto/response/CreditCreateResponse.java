package com.colendi.credit.dto.response;


import com.colendi.credit.dto.InstallmentDto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditCreateResponse extends BaseResponse implements Serializable {

    private Long creditId;
    private List<InstallmentDto> installments;
}
