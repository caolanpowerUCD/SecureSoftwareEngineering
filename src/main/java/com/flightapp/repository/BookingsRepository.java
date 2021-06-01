package com.flightapp.repository;

import com.flightapp.model.Bookings;
import com.flightapp.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long>{
    ArrayList<Bookings> findByCustomerEmail(String email);
    Bookings findByBookingID(Long BookingID);

    @Query(value="SELECT COUNT(*) FROM flightsystem.bookings WHERE flight_id = ?1", nativeQuery = true)
    int getNumberOfBookings(String flightId);

    @Query(value="SELECT * FROM flightsystem.bookings WHERE flight_id=?1 AND customer_email=?2", nativeQuery = true)
    ArrayList<Bookings> getBookingByFlightIdandEmail(String flightId, String email);
}