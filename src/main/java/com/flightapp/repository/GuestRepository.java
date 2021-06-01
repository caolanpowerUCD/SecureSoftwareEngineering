package com.flightapp.repository;

import com.flightapp.model.Guest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Guest findByEmail(String email);

}

