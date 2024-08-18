package com.colendi.credit.service;

import com.colendi.credit.exception.ColendiException;

import java.math.BigDecimal;

public interface ConfigService {
    BigDecimal findByKey(String key) throws ColendiException;
}
