package com.flightapp.validator;

import com.flightapp.model.ExecutiveUser;
import com.flightapp.model.CreditCard;
import com.flightapp.model.Flights;
import com.flightapp.model.Guest;
import com.flightapp.repository.ExecutiveUserRepository;

import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class InputValidator{

    private GenericValidator validator = new GenericValidator();

    @Autowired
    private ExecutiveUserRepository executiveUserRepository;

    // Validate the card details input.
    public void validateCardDetails(CreditCard creditCard, Errors errors){

        // Validate credit card number
        if (!validator.isCreditCard(creditCard.getCard_number()))
            errors.rejectValue("card_number", "InvalidCreditCardNumber");
    
        // Validate month
        if (!validator.matchRegexp(creditCard.getCard_expiry_month(), "^(0?[1-9]|1[012])$"))
            errors.rejectValue("card_expiry_month", "InvalidMonth");
    
        // Validate year
        if (!validator.matchRegexp(creditCard.getCard_expiry_year(), "^20(1[1-9]|[2-9][0-9])$"))
            errors.rejectValue("card_expiry_year", "InvalidYear");
    
        // Validate CVV
        if (!validator.matchRegexp(creditCard.getCard_cvv(), "^[0-9]{3,4}$"))
            errors.rejectValue("card_cvv", "InvalidCVV");
    }

    // Validate the input for executive user inputs.
    public void validateUserDetailsUpdate(ExecutiveUser user, Errors errors){

        // Validate First Name
        if (validator.isBlankOrNull(user.getFirst_name()))
            errors.rejectValue("first_name", "InvalidFirstName");
        // Validate Surname
        if (validator.isBlankOrNull(user.getLast_name()))
            errors.rejectValue("last_name", "InvalidLastName");
        // Validate Email
        if (!validator.isEmail(user.getEmail()))
            errors.rejectValue("email", "InvalidEmail");
        // Validate Address
        if (validator.isBlankOrNull(user.getAddress()))
            errors.rejectValue("address", "InvalidAddress");
        // Validate Username
        if (!validator.matchRegexp(user.getPhone_number(), "^(0[124-9]\\d{0,2})\\d{7}$"))
            errors.rejectValue("phone_number", "InvalidPhone");
    }

    public void valdiateGuestBooking(Guest guest, Errors errors){
         // Validate First Name
            if (validator.isBlankOrNull(guest.getFirst_name()))
                errors.rejectValue("first_name", "InvalidFirstName");
            // Validate Surname
            if (validator.isBlankOrNull(guest.getLast_name()))
                errors.rejectValue("last_name", "InvalidLastName");
            // Validate Email
            if (!validator.isEmail(guest.getEmail()))
                errors.rejectValue("email", "InvalidEmail");
            // Validate Address
            if (validator.isBlankOrNull(guest.getAddress()))
                errors.rejectValue("address", "InvalidAddress");
            // Validate Username
            if (!validator.matchRegexp(guest.getPhone_number(), "^(0[124-9]\\d{0,2})\\d{7}$"))
                errors.rejectValue("phone_number", "InvalidPhone");
    }

    public void validateFlights(String origin, String destination, Errors errors){
        if (!validator.matchRegexp(origin, "[A-Za-z]{3}"))
            errors.rejectValue("origin", "InvalidLocation");
        if (!validator.matchRegexp(destination, "[A-Za-z]{3}"))
            errors.rejectValue("destination", "InvalidLocation");
    }
}