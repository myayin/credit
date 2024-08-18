package com.colendi.credit.service.impl;

import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.repository.ConfigRepository;
import com.colendi.credit.service.ConfigService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.colendi.credit.constants.CreditResponseCodes.E_KEY_NOT_FOUND;

@Service
@AllArgsConstructor
public class ConfigServiceImpl implements ConfigService {
    private final ConfigRepository configRepository;

    @Cacheable(value = "configCache", key = "#key")
    public BigDecimal findByKey(String key) throws ColendiException {
        return configRepository.findByKey(key).orElseThrow(() -> new ColendiException(E_KEY_NOT_FOUND.name(), E_KEY_NOT_FOUND.getMessage())).getValue();
    }
}
