package com.flightapp.repository;

import com.flightapp.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Long> {
    @Query(value = "SELECT * FROM flightsystem.flights WHERE flight_origin= ?1 AND flight_destination = ?2", nativeQuery = true)
    ArrayList<Flights> searchFlightsByLocation(String flightOrigin, String flightDestination);

    @Query(value = "SELECT * FROM flightsystem.flights WHERE flight_origin= ?1 AND flight_destination = ?2 AND departure_date BETWEEN ?3 AND ?4 ORDER BY departure_date DESC", nativeQuery = true)
    ArrayList<Flights> searchFlightsByLocationAndDate(String flightOrigin, String flightDestination, String startDate, String endDate);

    @Query(value="SELECT * FROM flightsystem.flights WHERE flight_id=?1", nativeQuery = true)
    Flights getFlightById(String flightId);
}

