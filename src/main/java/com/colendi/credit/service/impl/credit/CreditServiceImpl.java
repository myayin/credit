package com.colendi.credit.service.impl.credit;

import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.dto.request.CreditCreateRequest;
import com.colendi.credit.dto.response.CreditCreateResponse;
import com.colendi.credit.model.Credit;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.model.User;
import com.colendi.credit.model.enums.BasicStatusEnum;
import com.colendi.credit.service.UserService;
import com.colendi.credit.service.credit.CreditService;
import com.colendi.credit.service.credit.CreditTransactionService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditTransactionService creditTransactionService;
    private final UserService userService;


    @Override
    public CreditCreateResponse createCredit(CreditCreateRequest request) throws ColendiException {
        Credit credit = getCredit(request);
        List<InstallmentDto> installments = creditTransactionService.saveCreditAndInstallments(credit,
                request.getInstallmentCount());
        return generateResponse(credit, installments);
    }

    private Credit getCredit(CreditCreateRequest request) throws ColendiException {
        Credit credit = new Credit();
        credit.setCreditAmount(request.getCreditAmount());
        credit.setCurrency(request.getCurrency());
        credit.setStatus(BasicStatusEnum.ACTIVE.name());
        credit.setCreditType(request.getCreditType());
        User user = userService.findById(request.getUserId());
        credit.setUser(user);
        credit.setCreatedDate(new Date().toInstant());
        return credit;
    }

    private CreditCreateResponse generateResponse(Credit credit, List<InstallmentDto> installmentDtos) {
        CreditCreateResponse creditCreateResponse = new CreditCreateResponse();
        creditCreateResponse.setCreditId(credit.getId());
        creditCreateResponse.setInstallments(installmentDtos);
        return creditCreateResponse;
    }
}
