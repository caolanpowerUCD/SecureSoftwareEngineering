package com.flightapp.validator;

import com.flightapp.service.UserService;
import com.flightapp.model.ExecutiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {




    private final String NAME_REGEX = "([A-Z][a-z]*).{2,32}";

    //private final String LAST_NAME_REGEX = "(^[\\p{L}\\s.’\\-,]+$).{2,32}";


    // (?=.*[a-z])   The string must contain at least 1 lowercase alphabetical character
// (?=.*[A-Z])	The string must contain at least 1 uppercase alphabetical character
// (?=.*[0-9])	The string must contain at least 1 numeric character
// (?=.*[!@#$%^&*])	The string must contain at least one special character, but we are escaping reserved RegEx characters to avoid conflict
// (?=.{8,32})	The string must be eight characters or longer up to 32 characters
    private final String PASSWORD_REGEX = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,32})";

    /*
    RFC 3522
     */

    //private final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]);

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ExecutiveUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ExecutiveUser user = (ExecutiveUser) o;
        if(!isValid(user.getFirst_name(), NAME_REGEX))
            errors.rejectValue("first_name", "InvalidFirstName");
        if(!isUserValid(user.getUsername()))
            errors.rejectValue("username","InvalidUsername");
        if(!isValid(user.getLast_name(), NAME_REGEX))
            errors.rejectValue("last_name","InvalidLastName");
        if(!isEmailValid(user.getEmail()))
            errors.rejectValue("email", "InvalidEmail");

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if ((user.getUsername().length() < 6 || user.getUsername().length() > 32) ||
                (userService.findByUsername(user.getUsername()) != null) ||
                (userService.findByEmail(user.getEmail())!=null) ||
            (userService.findByUsername(user.getUsername()) != null) ||
            (!user.getPasswordConfirm().equals(user.getPassword())) ||
            (!isValid(user.getPassword(), PASSWORD_REGEX))
        )
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
    }

    public void validatePassword(String password, Errors errors){
        if(!isValid(password, PASSWORD_REGEX))
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");


    }

    private boolean isValid(String toValidate, String regex) {

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(toValidate);
        return matcher.matches();

    }

    private boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isUserValid(String username){
        return username.matches("^[a-z0-9_-]{6,32}$");
    }






}
