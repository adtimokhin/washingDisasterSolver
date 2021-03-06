package com.adtimokhin.validation;

import com.adtimokhin.model.User;
import com.adtimokhin.service.UserService;
import com.adtimokhin.validation.errors.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Component
public class UserValidator {

    private static final int MIN_PASSWORD_LENGTH = 7;

    @Autowired
    private UserService userService;

    public List<UserError> validate(User user, String passwordTwo) {
        ArrayList<UserError> errors = new ArrayList<>(); // we will add UserErrors to this List.

        //check one - password is at least 7 characters long
        if (user.getPassword().equals("")) {
            errors.add(new UserError("Your password field is empty."));
        } else {
            if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
                errors.add(new UserError("Password should be at least " + MIN_PASSWORD_LENGTH + " characters long."));
            } else if (!user.getPassword().equals(passwordTwo)) {
                errors.add(new UserError("Passwords do not match."));
            }
        }
        //check two - email is in correct format
        if (!isEmailInCorrectFormat(user.getEmail())) {
            errors.add(new UserError("Email is in invalid form."));
        } else {
            // check three - email is not yet in use
            if (userService.findByEmail(user.getEmail()) != null) {
                errors.add(new UserError("This email is already in use."));
            }
        }

        if(user.getName().equals("")){
            errors.add(new UserError("You should enter your name."));
        }else if(user.getName().split(" ").length != 2){
            errors.add(new UserError("You should enter your name and surname."));
        }

        return errors;
    }

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static Pattern emailPattern = Pattern.compile(emailRegex);

    private boolean isEmailInCorrectFormat(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();

    }
}
