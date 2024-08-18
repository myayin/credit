package com.colendi.credit.dto.request;

import com.colendi.credit.dto.CreditDto;
import com.colendi.credit.dto.response.BaseResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GetCreditsByUserResponse extends BaseResponse {

    private List<CreditDto> creditDtoList;
}
