package com.colendi.credit.controller;

import com.colendi.credit.dto.request.CreditCreateRequest;
import com.colendi.credit.dto.request.GetCreditsByUserResponse;
import com.colendi.credit.dto.response.CreditCreateResponse;
import com.colendi.credit.dto.response.Result;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.service.credit.CreditService;
import com.colendi.credit.validation.CreditRequestValidator;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/v0/credit", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping(value = "/create")
    public ResponseEntity<CreditCreateResponse> createCredit(@RequestBody CreditCreateRequest request)
            throws ColendiException {
        CreditRequestValidator.validateCreateCredit(request);
        CreditCreateResponse response = creditService.createCredit(request);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<GetCreditsByUserResponse> getCreditsByUser(@PathVariable Long userId,
                                                                     @RequestParam(required = false) String status,
                                                                     @RequestParam(required = false) Date createdDate,
                                                                     Pageable pageable) throws ColendiException {
        CreditRequestValidator.validateGetCredit(userId);
        GetCreditsByUserResponse response = creditService.getCreditsByUser(userId, status, createdDate, pageable);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

}
