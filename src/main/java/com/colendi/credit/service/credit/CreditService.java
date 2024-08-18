package com.colendi.credit.service.credit;

import com.colendi.credit.dto.request.CreditCreateRequest;
import com.colendi.credit.dto.response.CreditCreateResponse;
import com.colendi.credit.exception.ColendiException;

public interface CreditService {
    CreditCreateResponse createCredit(CreditCreateRequest request) throws ColendiException;

}
