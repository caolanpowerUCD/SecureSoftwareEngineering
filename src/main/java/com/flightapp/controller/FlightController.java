package com.flightapp.controller;


import com.flightapp.model.Bookings;
import com.flightapp.entity.FlightDetails;
import com.flightapp.model.ExecutiveUser;
import com.flightapp.model.Flights;
import com.flightapp.repository.ExecutiveUserRepository;
import com.flightapp.repository.FlightsRepository;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FlightController {

    Logger logger = LoggerFactory.getLogger(FlightController.class);
    @Autowired
    ExecutiveUserRepository executiveUserRepository;
    @Autowired
    FlightsRepository flightRepository;

    @RequestMapping("/")
    public String homepage(){
        return "homepage";
    }

    @GetMapping("/search")
    public String search(){
        return "homepage";
    }

    @PostMapping("/search")
    public String searchFlights(String origin, String destination, String startDate, String endDate, Model model){
        ArrayList<Flights> flights = new ArrayList<Flights>();
        String today = "2021-02-01" + "%"; //Percentage is to activate the LIKE operator in the SQL Query so we can search by just date.
        String distantFuture = new Date(7777777777777L).toString() + "%"; // Thu Jun 20 2216 13:49:37

        if (origin.isEmpty() && destination.isEmpty() && startDate.isEmpty() && endDate.isEmpty())
            flights = (ArrayList<Flights>) flightRepository.findAll();
        // If no dates provided, look up all flights in database between two locations
        else if (startDate.isEmpty() && endDate.isEmpty())
            flights = flightRepository.searchFlightsByLocation(origin.trim(), destination.trim());
        // If no start date, look for flights between today and the provided end date.
        else if (startDate.isEmpty()) {
            String endDateQ = endDate + "%";
            flights = flightRepository.searchFlightsByLocationAndDate(origin.trim(), destination.trim(), today, endDateQ);
        }
        // If no end date, look for flights between start date and a date in
        else{
            String startDateQ = startDate + "%";
            flights = flightRepository.searchFlightsByLocationAndDate(origin.trim(), destination.trim(), startDateQ, distantFuture);
        }

        model.addAttribute("listFlights", flights);
        return "homepage";
    }

    @PostMapping("/search/user")
    public String searchUserFlights(String origin, String destination, String startDate, String endDate, Model model){
        ArrayList<Flights> flights = new ArrayList<Flights>();
        String today = "2021-02-01" + "%"; //Percentage is to activate the LIKE operator in the SQL Query so we can search by just date.
        String distantFuture = new Date(7777777777777L).toString() + "%"; // Thu Jun 20 2216 13:49:37

        if (origin.isEmpty() && destination.isEmpty() && startDate.isEmpty() && endDate.isEmpty())
            flights = (ArrayList<Flights>) flightRepository.findAll();
            // If no dates provided, look up all flights in database between two locations
        else if (startDate.isEmpty() && endDate.isEmpty())
            flights = flightRepository.searchFlightsByLocation(origin.trim(), destination.trim());
            // If no start date, look for flights between today and the provided end date.
        else if (startDate.isEmpty()) {
            String endDateQ = endDate + "%";
            flights = flightRepository.searchFlightsByLocationAndDate(origin.trim(), destination.trim(), today, endDateQ);
        }
        // If no end date, look for flights between start date and a date in
        else{
            String startDateQ = startDate + "%";
            flights = flightRepository.searchFlightsByLocationAndDate(origin.trim(), destination.trim(), startDateQ, distantFuture);
        }

        model.addAttribute("listFlights", flights);
        return "executiveuserhome";
    }

}
