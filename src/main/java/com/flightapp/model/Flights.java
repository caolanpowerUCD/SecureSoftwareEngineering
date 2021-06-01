package com.flightapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.persistence.GeneratedValue;
import java.sql.Date;

@Entity
@Table(name = "flights")
public class Flights {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private String flightId;

    @NotBlank
    @Getter @Setter
    private String flightOrigin;

    @NotBlank
    @Getter @Setter
    private String flightDestination;

    @NotBlank
    @Getter @Setter
    private String departureDate;

    @NotBlank
    @Getter @Setter
    private int totalSeats;

    public Flights(){}

    public Flights (String flightId, String flightOrigin, String flightDestination, String departureDate, int totalSeats){
        this.setFlightId(flightId);
        this.setFlightOrigin(flightOrigin);
        this.setFlightDestination(flightDestination);
        this.setDepartureDate(departureDate);
        this.setTotalSeats(totalSeats);
    }

}
