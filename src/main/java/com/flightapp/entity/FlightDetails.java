package com.flightapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class FlightDetails {
    @NotBlank
    @Getter @Setter
    private String flightOrigin;
    @NotBlank
    @Getter @Setter
    private String flightDestination;
    @Getter @Setter
    private Date startDate;
    @Getter @Setter
    private Date endDate;

    public FlightDetails (){ }

    public FlightDetails(String flightOrigin, String flightDestination, Date startDate, Date endDate){
        this.setFlightOrigin(flightOrigin);
        this.setFlightDestination(flightDestination);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public boolean isEmptyStartDate(){
        return this.startDate == null;
    }

    public boolean isEmptyDates(){
        return this.getStartDate() == null && this.getEndDate() == null;
    }

}