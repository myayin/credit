package com.colendi.credit.exception;

import lombok.Getter;

@Getter
public class ColendiException extends Exception {

    private final String errorCode;

    public ColendiException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
