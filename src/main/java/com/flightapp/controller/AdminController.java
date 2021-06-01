package com.flightapp.controller;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.flightapp.model.Bookings;
import com.flightapp.model.ExecutiveUser;
import com.flightapp.model.Flights;
import com.flightapp.model.Role;
import com.flightapp.repository.BookingsRepository;
import com.flightapp.repository.ExecutiveUserRepository;
import com.flightapp.repository.FlightsRepository;
import com.flightapp.repository.GuestRepository;
import com.flightapp.repository.RoleRepository;
import com.flightapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExecutiveUserRepository executiveUserRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private FlightsRepository flightRepository;

    @Autowired
    private RoleRepository roleRepository;

    

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole))
            return "admin";
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
    }

    @PostMapping("/adminSearchFlights")
    public String searchFlights(String origin, String destination, String startDate, String endDate, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
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
        return "flight";
    }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
    }

    @PostMapping("/findBookings")
    public String editBookings(HttpServletResponse response, String email, Model model)throws Exception{
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            ArrayList<Bookings> bookings = new ArrayList<Bookings>();
            bookings = bookingsRepository.findByCustomerEmail(email);
            ArrayList<Flights> flights = new ArrayList<Flights>();

            for (Bookings booking : bookings) {
                flights.add(flightRepository.getFlightById(booking.getFlightId()));
            }

            model.addAttribute("listFlights", flights);
            model.addAttribute("bookings", bookings);
            return "booking";
        }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
    }

    @GetMapping("/editBookings")
    public String getEditBookings(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole))
            return "booking";
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
    }

    @GetMapping("/editFlights")
    public String getEditFlights(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole))
            return "flight";
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
    }
    
    @GetMapping("/edit/{flightId}")
    public String editFlights(@PathVariable("flightId") String flightId, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            Flights flight = flightRepository.getFlightById(flightId);
            model.addAttribute("flightdetails", flight);
            return "editflight";
        }   
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
    }

    @PostMapping("/doEditFlight/{flightId}")
    public String doEditFlights(@PathVariable("flightId") String flightId, String flightOrigin, String flightDestination, String departureDate, String totalSeats, Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            Flights newFlight = flightRepository.getFlightById(flightId);
            int totalSeatsINT = Integer.parseInt(totalSeats);

            newFlight.setFlightOrigin(flightOrigin);
            newFlight.setFlightDestination(flightDestination);
            newFlight.setDepartureDate(departureDate);
            newFlight.setTotalSeats(totalSeatsINT);

            flightRepository.save(newFlight);
            model.addAttribute("message", "Details of flight " + flightId + " were updated successfully");
            return "admin";
    }
    else {
        model.addAttribute("message", "You do not have the permissions to perform that action");
        return "executiveuserhome";
    }
        
    }

     @GetMapping("/edit/booking/{bookingID}")
     public String editBooking(@PathVariable("bookingID") String bookingID, Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            long bookingIDLong = Long.parseLong(bookingID);
            Bookings booking = bookingsRepository.findByBookingID(bookingIDLong);
            model.addAttribute("booking", booking);
            return "editbooking";
        }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
     }

     @PostMapping("/doEditBooking/{bookingID}")
     public String doEditBooking(@PathVariable(value ="bookingID") String bookingID, String flightId, String customerEmail, Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            if(flightId == "" || customerEmail == "") return "admin";
            long bookingIDLong = Long.parseLong(bookingID);
            Bookings newBooking = bookingsRepository.findByBookingID(bookingIDLong);
            newBooking.setFlightId(flightId);
            newBooking.setCustomerEmail(customerEmail);

            bookingsRepository.save(newBooking);
            model.addAttribute("message", "Booking was updated successfully");
            return "admin";
        }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
     }

     @GetMapping("/deleteBooking/{bookingID}")
     public String deleteBooking(@PathVariable("bookingID") String bookingID, Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            Long bookingIDINT = Long.parseLong(bookingID);       
            Bookings booking = bookingsRepository.findByBookingID(bookingIDINT);
            bookingsRepository.delete(booking);
            model.addAttribute("message", "Booking " + bookingID + " was deleted successfully");
            return "admin";
        }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
     }

     @GetMapping("/deleteFlight/{flightId}")
     public String deleteFlight(@PathVariable("flightId") String flightId, Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            Flights flight = flightRepository.getFlightById(flightId);
            flightRepository.delete(flight);
            model.addAttribute("message", "Flight " + flightId + " was deleted successfully");
            return "admin";
        }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        } 
       
     }

     @GetMapping("/addFlight")
     public String getAddFlight(Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole))
            return "addflight";
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
        
     }

     @PostMapping("/addFlight")
     public String addFlight(String flightId, String flightOrigin, String flightDestination, String departureDate, int totalSeats, Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if(user.getRoles().contains(checkRole)) {
            Flights newFlight = new Flights(flightId, flightOrigin, flightDestination, departureDate, totalSeats);
            flightRepository.save(newFlight);
            return "admin";
        }
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
       }
        
     }

     @GetMapping("/addBooking")
     public String getAddBooking(Model model){
        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }
        else {
            return "login";
       }
        if(user.getRoles().contains(checkRole))
            return "addbooking";
        else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
        }
    }

     @PostMapping("/addBooking")
     public String addBooking(String flightId, String customerEmail, Model model){

        //check if user is admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        Role checkRole = roleRepository.findByName("ADMIN");
        
        if (userDetails.toString() != "anonymousUser") {
            user = userService.findByUsername(userDetails.toString());
        }
        else {
            return "login";
       }
        if(user.getRoles().contains(checkRole)) {
            Bookings newBooking = new Bookings();

            newBooking.setFlightId(flightId);
            newBooking.setCustomerEmail(customerEmail);

            bookingsRepository.save(newBooking);
            model.addAttribute("message", "Booking created successfully");
            return "admin";
        }
       else {
            model.addAttribute("message", "You do not have the permissions to perform that action");
            return "executiveuserhome";
       }
         
     }

}
