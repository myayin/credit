package com.colendi.credit.dto;


import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class CreditFilter {

    private Long userId;
    private String status;
    private Date createdDate;

    public Optional<String> getStatus() {
        return Optional.ofNullable(status);
    }

    public Optional<Date> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }
}
