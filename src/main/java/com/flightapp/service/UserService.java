package com.flightapp.service;

import com.flightapp.model.ExecutiveUser;

import com.flightapp.model.ConfirmationToken;

public interface UserService {
    void save(ExecutiveUser user);

    ExecutiveUser findByUsername(String username);

    ExecutiveUser findByEmail(String email);

    void save(ConfirmationToken confirmationToken);

    ExecutiveUser isValidToken(String confirmationToken);

    void savePassword(String email, String password);

}
