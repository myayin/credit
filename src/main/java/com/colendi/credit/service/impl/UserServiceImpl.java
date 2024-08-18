package com.colendi.credit.service.impl;

import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.model.User;
import com.colendi.credit.repository.UserRepository;
import com.colendi.credit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.colendi.credit.constants.CreditResponseCodes.E_USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findById(Long id) throws ColendiException {
        return userRepository.findById(id).orElseThrow(() -> new ColendiException(E_USER_NOT_FOUND.name(), E_USER_NOT_FOUND.getMessage()));
    }
}
