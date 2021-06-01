package com.flightapp.repository;

import com.flightapp.model.Keys;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeysRepository extends JpaRepository<Keys, Long> {
    Optional<Keys> findByCardID(Long cardID);

}
