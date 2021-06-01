package com.flightapp.repository;
import java.util.Optional;

import com.flightapp.model.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByUserID(Long userID);

}
