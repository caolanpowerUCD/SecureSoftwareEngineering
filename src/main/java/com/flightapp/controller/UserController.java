package com.flightapp.controller;

import com.flightapp.model.Bookings;
import com.flightapp.model.CreditCard;
import com.flightapp.model.ExecutiveUser;
import com.flightapp.model.Flights;
import com.flightapp.model.Guest;
import com.flightapp.model.Keys;
import com.flightapp.model.Role;
import com.flightapp.repository.BookingsRepository;
import com.flightapp.repository.CreditCardRepository;
import com.flightapp.repository.ExecutiveUserRepository;
import com.flightapp.repository.FlightsRepository;
import com.flightapp.repository.GuestRepository;
import com.flightapp.repository.KeysRepository;
import com.flightapp.repository.RoleRepository;
import com.flightapp.service.ACUserDetails;
import com.flightapp.service.SecurityService;
import com.flightapp.service.SecurityServiceImpl;
import com.flightapp.service.UserService;
import com.flightapp.session.ExecutiveUserSession;
import com.flightapp.validator.InputValidator;
import com.flightapp.validator.UserValidator;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.dialect.identity.SybaseAnywhereIdentityColumnSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

import java.util.ArrayList;


@Controller
public class UserController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;
    
    // Password Encoder
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // Logger object for logs
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ExecutiveUserRepository executiveUserRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private FlightsRepository flightsRepository;

    @Autowired
    private KeysRepository keysRepository;

    @Autowired
    private CreditCardRepository cardRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired 
    private InputValidator inputValidator;

    @Autowired
    private RoleRepository roleRepository;



    @GetMapping("/executiveUserHome")
    public String userHome(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "homepage";
        }
        
        logger.warn("GET Request - Executive User Homepage. Is the user logged in?");
        return "executiveuserhome";
    }

    @GetMapping("/register")
    public String register(Model model) {
        logger.info("GET Request - Registration.");
        model.addAttribute("userForm", new ExecutiveUser());

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userForm") ExecutiveUser userForm, BindingResult bindingResult) {
        logger.info("Attempting to register new user to the system.");
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("Error occurred during registration - invalid details used.");
            return "register";
        }

        userService.save(userForm);
        logger.info("Successfully registered " + userForm.getUsername() + " to the system.");
        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/executiveUserHome";
    }

    @PostMapping("/login")
    public String login(Model model) throws Exception {
        model.addAttribute("error", "The link is invalid or broken!");
        logger.error("Login - failed...loading error page.");

        return "loginfailed";
        //return "error";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        logger.info("GET Request - logging in.");
        return "login";
    }

    @GetMapping("/loginFailed")
    public String loginFailed() {
        logger.info("GET Request - login failed.");
        return "loginfailed";
    }


    // Method which displays all bookings made by the signed in user
    @GetMapping("/executiveUserBookings")
    public String getUserBookings(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }

        String email = user.getEmail();
        ArrayList<Bookings> userBookings = new ArrayList<Bookings>();
        userBookings = bookingsRepository.findByCustomerEmail(email);
        ArrayList<Flights> flights = new ArrayList<Flights>();
        logger.info("Retrieving all reservations for user " + user.getId());
        for (Bookings bookings: userBookings){
            flights.add(flightsRepository.getFlightById(bookings.getFlightId()));
        }
        model.addAttribute("listFlights", flights);
        return "executiveuserbookings";
    }

    @GetMapping("/carddetails")
    public String updateCardDetails(Model model){
        logger.info("GET Request - update card details.");
        model.addAttribute("ccform", new CreditCard());
        return "carddetails";
    }

    // Method for saving a user's card details
    @PostMapping("/carddetails")
    public String updateCardDetails(@ModelAttribute("ccform") CreditCard ccform, Model model, BindingResult bindingResult){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            logger.error("Unauthorized user tried to update card details - returned to homepage.");
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        if (userDetails != null) {
            ExecutiveUser user = userService.findByUsername(userDetails.toString());
            CreditCard encryptedCard = encryptCreditCardDetails(ccform);
            
            if (encryptedCard == null) {
                logger.error("Problem occurred when saving card details.");
                model.addAttribute("error", "There was a problem saving your details please try again");
                return "carddetails";
            }
            else {
                logger.info("Validating credit card information for user " + user.getId());
                inputValidator.validateCardDetails(ccform, bindingResult);
                if (bindingResult.hasErrors()) {
                    logger.info("Incorrect card details inputted, user must try again.");
                    return "carddetails";
                }
                encryptedCard.setUserID(user.getId());
                logger.info("Updating card details for user " + user.getId());
                cardRepository.save(encryptedCard);
                return "executiveuserhome";
            }
            
        }
        else { return "carddetails"; }
    }

    @GetMapping("/delete")
    public String deleteUser(){
        logger.warn("GET Request - deleting user.");
        return "deleteuser";
    }

    // Method for deleting a user's account
    @PostMapping("/delete")
    public String deleteUser(String email, String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        // Check username and password are correct
        if (user.getEmail().equals(email) && passwordEncoder.matches(password, user.getPassword()) == true) {
            logger.info("Deleting account of user "+user.getId());
            executiveUserRepository.delete(user);
            return "homepage";
        }
        else { 
            logger.error("Error occurred when attempting to delete user "+user.getId());
            return "deleteuserfailed"; 
        }
    }

    @GetMapping("/update")
    public String updateDetails(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        logger.info("User"+ user.getId() + " updating personal details.");
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        model.addAttribute("updateUserForm", user);
        return "updatedetails";
    }

    // Method for updating user's personal details
    @PostMapping("/update")
    public String updateDetails(@ModelAttribute("updateUserForm") ExecutiveUser updatedUser, Model model, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            logger.warn("Unauthorized access trying to update personal user details. Returning to homepage.");
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }
        if (user != null) {
            // Validate user details before updating
            inputValidator.validateUserDetailsUpdate(updatedUser, bindingResult);

            if (bindingResult.hasErrors()) {
                logger.warn("Error occurred when updating user "+ user.getId() + " details- invalid details used. Trying again.");
                return "updatedetails";
            }
            // Do not change if input was empty
            if (!updatedUser.getFirst_name().equals("")) {
                user.setFirst_name(updatedUser.getFirst_name());
            }
            if (!updatedUser.getLast_name().equals("")) {
                user.setLast_name(updatedUser.getLast_name());
            }
            if (!updatedUser.getEmail().equals("")) {
                user.setEmail(updatedUser.getEmail());
            }
            if (!updatedUser.getAddress().equals("")) {
                user.setAddress(updatedUser.getAddress());
            }
            if (!updatedUser.getPhone_number().equals("")) {
                user.setPhone_number(updatedUser.getPhone_number());
            }
            logger.info("Updating personal user details of user "+user.getId());
            executiveUserRepository.save(user);
            return "executiveuserhome";
        }
        else { return "updatedetails"; }
    }

    @GetMapping("/book/{flightId}")
    public String bookFlight(@PathVariable("flightId") String flightId, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }

        Flights flight = flightsRepository.getFlightById(flightId);
        boolean hasBooked = false;
        boolean userLoggedIn = user != null;
        if (userLoggedIn) {
            if (bookingsRepository.getBookingByFlightIdandEmail(flightId,user.getEmail()) != null) { //If the user has already booked a ticket for themselves
                logger.warn("User " + user.getId() + " has already booked flight "+flightId);
                hasBooked = true;
            }
        }
        int totalSeats = flight.getTotalSeats();
        int availableSeats = flight.getTotalSeats() - bookingsRepository.getNumberOfBookings(flightId); //Find number of available seats
        String seatNum = availableSeats+"/"+totalSeats;
        model.addAttribute("availableSeats", seatNum);
        model.addAttribute("flightData", flight);
        if (availableSeats > 0) {
            if (!userLoggedIn && !hasBooked) {
                logger.info("Guest booking flight " + flightId + ".");
                return "guestbookflights";
            } else {
                model.addAttribute("userData", user);
                logger.info("User " + user.getId() + " wants to book flight " + flightId + ".");
                return "userbookflights";
            }
        }
        else{
            logger.warn("No seats to book. User returned to homepage.");
            return "homepage";
        }
    }

    @PostMapping("/bookFlight")
    public String bookFlight(String first_name, String last_name, String email, String address, String number, String flightId, String ccnum, String expiry_month, String expiry_year, String cvv, Model model){
        if (bookingsRepository.findByCustomerEmail(email.trim()) != null){
            if (first_name != "" && last_name != "" && email != "" && address != "" &&
                number != "" && ccnum != "" && expiry_month != "" && expiry_year != "" && cvv != ""){
                Bookings booking = new Bookings(flightId, email);
                logger.info("Flight "+ flightId + " has been booked by " + email);
                bookingsRepository.save(booking);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Object userDetails = auth.getPrincipal();
                if (userDetails.toString() == "anonymousUser") { // If a guest books the flight, store their data
                    logger.info("Storing the guest's details.");
                    Guest guest = new Guest(first_name, last_name, address, email, number);
                    //CreditCard encryptedCard = encryptCreditCardDetails(ccnum, expiry_month, expiry_year, cvv);
                    guestRepository.save(guest);
                }
                model.addAttribute("flightId", flightId);
                model.addAttribute("email", email);
                return "confirmation";
            }
            
            logger.warn("Guest did not put in all the right details during booking.");
        }
        return "homepage";
    }

    @GetMapping("/findGuestBookings")
    public String findGuestBookings(){
        return "findguestbookings";
    }

    @PostMapping("/findGuestBookings")
    public String getGuestBookings(HttpServletResponse response, String first_name, String last_name, String email, String address, String number, Model model)throws Exception{
        Guest user = guestRepository.findByEmail(email);
        boolean detailsCorrect = true;

        if (!user.getFirst_name().equals(first_name)) {
            detailsCorrect = false;
        } else if (!user.getLast_name().equals(last_name)) {
            detailsCorrect = false;
        } else if (!user.getAddress().equals(address)) {
            detailsCorrect = false;
        } else if (!user.getPhone_number().equals(number)) {
            detailsCorrect = false;
        }

        if (detailsCorrect == true) {
            logger.info("Retrieving guest booking details for guest "+email+".");
            ArrayList<Bookings> guestBookings = new ArrayList<Bookings>();
            guestBookings = bookingsRepository.findByCustomerEmail(email);
            ArrayList<Flights> flights = new ArrayList<Flights>();

            for (Bookings bookings : guestBookings) {
                flights.add(flightsRepository.getFlightById(bookings.getFlightId()));
            }
            model.addAttribute("listFlights", flights);
            return "guestbooking";
         } else {
            logger.error("Error occurred trying to retrieve bookings for guest " + email);
            return "findguestbookingsfailed";
        }    
    }

    @RequestMapping("/cancel/{id}")
    public String cancelUserBooking(@PathVariable(value = "id") String flightId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
        }else{
            return "login";
        }

        String email = user.getEmail();
        ArrayList<Bookings> bookings = bookingsRepository.getBookingByFlightIdandEmail(flightId, email);
        Bookings booking = bookings.get(0);
        logger.info("User " + user.getId() + " cancelled their booking with ID " + booking.getBookingID());
        bookingsRepository.delete(booking);
        return getUserBookings(model);
    }

    @PostMapping("/guestcancel/{id}")
    public String cancelGuestBooking(@PathVariable(value = "id") String flightId, String email, String ccnum, String expiry_month, String expiry_year, String cvv, Model model) {
        ArrayList<Bookings> bookings = bookingsRepository.getBookingByFlightIdandEmail(flightId, email);
        Bookings booking = bookings.get(0);
        logger.info("Guest " + email + " cancelled their booking with ID " + booking.getBookingID());
        bookingsRepository.delete(booking);
        return "homepage";
    }

    @RequestMapping("/guestcancel/{id}")
    public String guestCancel(@PathVariable(value = "id") String flightId, Model model){
        model.addAttribute("FlightId", flightId);
        return "guestcancel";
    }

    @GetMapping("/password")
    public String updatePassword(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            logger.warn("GET Request - change user password. Is user logged in?");
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        ExecutiveUser user = new ExecutiveUser();
        
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.toString());
            logger.info("User " +  user.getId() + " changing password.");
        }else{
            return "login";
        }
        model.addAttribute("user", user);
        return "passwordchange";
    }

    // Method for updating user's personal details
    @PostMapping("/password")
    public String updatePassword(String oldPassword, String newPassword, Model model, @ModelAttribute("updateForm") ExecutiveUser updateForm, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            logger.warn("User not authorized to change password.");
            return "homepage";
        }
        Object userDetails = auth.getPrincipal();
        if (userDetails != null) {
            ExecutiveUser user= userService.findByUsername(userDetails.toString());
            if (passwordEncoder.matches(oldPassword, user.getPassword()) == true) {
                userValidator.validatePassword(newPassword, bindingResult);
                if (bindingResult.hasErrors() == true) {
                    model.addAttribute("error", "New password does not meet the requirements. Please try again.");
                    logger.warn("User " + user.getId() + " has unsuccessfully updated password and awaits retry.");
                    return "passwordchange";
                }
                else {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    executiveUserRepository.save(user);
                    logger.info("User " + user.getId() + " has successfully changed their password.");
                    model.addAttribute("success", "Password reset successfully");
                    return "passwordchange";
                }
            }
            // If password is incorrect allow them to retry
            else {
                logger.warn("User " + user.getId() + " has incorrectly entered in their password.");
                model.addAttribute("message",  "Password is incorrect please try again.");
                return "passwordchange"; 
            }
        }
        else {
            logger.error("User attempting to change password is not authorized, returned to homepage.");
            return "homepage";
        }
        
    }

    public CreditCard encryptCreditCardDetails(CreditCard ccform) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            final int AES_KEYLENGTH = 128;  
            byte[] iv = new byte[AES_KEYLENGTH / 8];    
            SecureRandom prng = new SecureRandom();
            prng.nextBytes(iv);

            Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5PADDING"); 

            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey,
                    new IvParameterSpec(iv));

            // Encode details
            byte[] number = ccform.getCard_number().getBytes();
            byte[] byteNumber = aesCipherForEncryption.doFinal(number);
            String encodedNumber = Base64.getEncoder().withoutPadding().encodeToString(byteNumber);

            byte[] expiry_month_byte = ccform.getCard_expiry_month().getBytes();
            byte[] byteMonth = aesCipherForEncryption.doFinal(expiry_month_byte);
            String encodedMonth = Base64.getEncoder().withoutPadding().encodeToString(byteMonth);

            byte[] expiry_year_byte = ccform.getCard_expiry_year().getBytes();
            byte[] byteYear = aesCipherForEncryption.doFinal(expiry_year_byte);
            String encodedYear = Base64.getEncoder().withoutPadding().encodeToString(byteYear);

            byte[] cvv_byte = ccform.getCard_cvv().getBytes();
            byte[] byteCvv = aesCipherForEncryption.doFinal(cvv_byte);
            String encodedCvv = Base64.getEncoder().withoutPadding().encodeToString(byteCvv);


            String ivString = new String(iv);
            String secretKeyString = new String(secretKey.getEncoded());
            
            // Create Credit Card and Keys object and save
            CreditCard encodedCard = new CreditCard(encodedNumber, encodedMonth, encodedYear, encodedCvv);
            cardRepository.save(encodedCard);
            Keys cardKey = new Keys(encodedCard.getId(), secretKeyString, ivString);
            keysRepository.save(cardKey);
            encodedCard.setKeyID(cardKey.getId());
            
            return encodedCard;
            
        }

        catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
        }

        catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
        }

        catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
        }

        catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
        }

        catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        }

        catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
        }
        return null;
    }

        public CreditCard decryptCreditCardDetails(Long userID) {
        CreditCard encryptedCard = cardRepository.findByUserID(userID).orElse(null);
        Keys userKey = keysRepository.findByCardID(encryptedCard.getId()).orElse(null);
        if (userKey != null) {
            try {
                Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!

                byte[] byteNumber = Base64.getDecoder().decode(encryptedCard.getCard_number());
                byte[] byteMonth = Base64.getDecoder().decode(encryptedCard.getCard_expiry_month());
                byte[] byteYear = Base64.getDecoder().decode(encryptedCard.getCard_expiry_year());
                byte[] byteCvv = Base64.getDecoder().decode(encryptedCard.getCard_cvv());
                
                byte[] iv = userKey.getIv().getBytes();

                byte[] encodedKey = userKey.getSecretKey().getBytes();
                SecretKey originalKey = new SecretKeySpec(encodedKey, "AES");


                aesCipherForDecryption.init(Cipher.DECRYPT_MODE, originalKey,
                        new IvParameterSpec(iv));
                byte[] decryptedNumber = aesCipherForDecryption.doFinal(byteNumber);
                String decryptedNumberString = new String(decryptedNumber);
                byte[] decryptedMonth = aesCipherForDecryption.doFinal(byteMonth);
                String decryptedMonthString = new String(decryptedMonth);
                byte[] decryptedYear = aesCipherForDecryption.doFinal(byteYear);
                String decryptedYearString = new String(decryptedYear);
                byte[] decryptedCvv = aesCipherForDecryption.doFinal(byteCvv);
                String decryptedCvvString = new String(decryptedCvv);
                return new CreditCard(decryptedNumberString, decryptedMonthString, decryptedYearString, decryptedCvvString);
            }
            catch (NoSuchAlgorithmException noSuchAlgo) {
                System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            }
    
            catch (NoSuchPaddingException noSuchPad) {
                System.out.println(" No Such Padding exists " + noSuchPad);
            }
    
            catch (InvalidKeyException invalidKey) {
                System.out.println(" Invalid Key " + invalidKey);
            }
    
            catch (BadPaddingException badPadding) {
                System.out.println(" Bad Padding " + badPadding);
            }
    
            catch (IllegalBlockSizeException illegalBlockSize) {
                System.out.println(" Illegal Block Size " + illegalBlockSize);
            }
    
            catch (InvalidAlgorithmParameterException invalidParam) {
                System.out.println(" Invalid Parameter " + invalidParam);
            }
            
        }
        
        return null;
           
    }
        
}
