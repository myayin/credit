package com.colendi.credit.service;


import com.colendi.credit.dto.InstallmentDto;
import com.colendi.credit.dto.request.CreditCreateRequest;
import com.colendi.credit.dto.response.CreditCreateResponse;
import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.model.Credit;
import com.colendi.credit.model.User;
import com.colendi.credit.service.credit.CreditTransactionService;
import com.colendi.credit.service.impl.credit.CreditServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.colendi.credit.constants.CreditResponseCodes.E_USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceCreateCreditTest {

    @Mock
    private CreditTransactionService creditTransactionService;

    @InjectMocks
    private CreditServiceImpl creditService;

    @Mock
    private UserService userService;

    @Test
    void shouldCreateCreditAndGenerateProperResponse() throws ColendiException {
        //Given
        CreditCreateRequest request = new CreditCreateRequest();
        request.setUserId(1L);
        request.setCreditAmount(BigDecimal.valueOf(1000));
        request.setCurrency("USD");
        request.setCreditType("HOME");
        request.setInstallmentCount(2);

        List<InstallmentDto> installmentDtos = List.of(
                new InstallmentDto().setId(1L).setAmount(BigDecimal.valueOf(500)).setCurrency("USD"),
                new InstallmentDto().setId(2L).setAmount(BigDecimal.valueOf(100)).setCurrency("USD"));
        ArgumentCaptor<Credit> creditCaptor = forClass(Credit.class);

        when(creditTransactionService.saveCreditAndInstallments(creditCaptor.capture(), eq(2)))
                .thenReturn(installmentDtos);
        when(userService.findById(1L))
                .thenReturn(new User().setId(1L).setFirstName("name").setLastName("lastName"));

        //When
        CreditCreateResponse response = creditService.createCredit(request);

        //Then
        assertEquals(installmentDtos.size(), response.getInstallments().size());
        assertEquals(installmentDtos.get(0).getAmount(), response.getInstallments().get(0).getAmount());
        assertEquals(installmentDtos.get(0).getCurrency(), response.getInstallments().get(0).getCurrency());
        assertEquals(installmentDtos.get(0).getId(), response.getInstallments().get(0).getId());
        verify(userService, times(1)).findById(1L);

        verify(creditTransactionService, times(1)).saveCreditAndInstallments(Mockito.any(Credit.class), eq(2));
        Credit capturedCredit = creditCaptor.getValue();
        assertEquals(request.getCreditAmount(), capturedCredit.getCreditAmount());
        assertEquals(request.getCurrency(), capturedCredit.getCurrency());
        assertEquals(request.getCreditType(), capturedCredit.getCreditType());
        assertEquals(request.getUserId(), capturedCredit.getUser().getId());
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() throws ColendiException {
        // Given
        CreditCreateRequest request = new CreditCreateRequest();
        request.setUserId(1L);
        request.setCreditAmount(BigDecimal.valueOf(1000));
        request.setCurrency("USD");
        request.setCreditType("HOME");
        request.setInstallmentCount(2);

        when(userService.findById(1L)).thenThrow(
                new ColendiException(E_USER_NOT_FOUND.name(), E_USER_NOT_FOUND.getMessage()));

        // When
        ColendiException exception = assertThrows(ColendiException.class, () -> creditService.createCredit(request));

        //Then
        Assertions.assertEquals(E_USER_NOT_FOUND.name(), exception.getErrorCode());
        verify(userService, times(1)).findById(1L);
        verify(creditTransactionService, never()).saveCreditAndInstallments(any(Credit.class), eq(2));
    }
}
