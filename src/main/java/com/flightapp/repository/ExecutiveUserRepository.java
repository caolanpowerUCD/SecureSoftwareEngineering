package com.flightapp.repository;

import com.flightapp.model.ExecutiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ExecutiveUserRepository extends JpaRepository<ExecutiveUser, Long> {
    ExecutiveUser findByEmailAndPassword(String email, String password);
    ExecutiveUser findByEmail(String email);
    ExecutiveUser findByUsername(String username);

    // @Query("UPDATE ExecutiveUser u SET u.failed_attempts = ?1 WHERE u.email = ?2")
    // @Modifying
    // @Transactional
    // public void updateFailedAttempts(int failAttempts, String email);

}
