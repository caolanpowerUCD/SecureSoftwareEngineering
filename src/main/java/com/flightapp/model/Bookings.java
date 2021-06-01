package com.flightapp.model;

import lombok.Setter;
import lombok.Getter;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingID;
    
    @Getter @Setter
    private String flightId;

    @NotBlank
    @Email
    @Getter @Setter
    private String customerEmail;

    public Bookings(){ }

    public Bookings(String flightId, String customerEmail) {
        this.setFlightId(flightId);
        this.setCustomerEmail(customerEmail);
    }
}