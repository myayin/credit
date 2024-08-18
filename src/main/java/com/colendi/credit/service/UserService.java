package com.colendi.credit.service;

import com.colendi.credit.exception.ColendiException;
import com.colendi.credit.model.User;

public interface UserService {
    User findById(Long id) throws ColendiException;
}
