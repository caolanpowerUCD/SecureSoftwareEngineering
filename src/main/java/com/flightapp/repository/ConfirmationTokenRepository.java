package com.flightapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.flightapp.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}