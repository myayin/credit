package com.colendi.credit.service.credit;

import com.colendi.credit.dto.request.CreditCreateRequest;
import com.colendi.credit.dto.request.GetCreditsByUserResponse;
import com.colendi.credit.dto.response.CreditCreateResponse;
import com.colendi.credit.exception.ColendiException;
import java.util.Date;
import org.springframework.data.domain.Pageable;

public interface CreditService {

    CreditCreateResponse createCredit(CreditCreateRequest request) throws ColendiException;

    GetCreditsByUserResponse getCreditsByUser(Long userId, String status, Date createdDate, Pageable pageable);

}
