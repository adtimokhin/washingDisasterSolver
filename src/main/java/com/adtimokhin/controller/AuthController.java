package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.UserService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.validation.UserValidator;
import com.adtimokhin.validation.errors.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Controller
public class AuthController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private WashingBookingMachineService washingMachineService;

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @GetMapping("/login")
    public String loginGet(@RequestParam(value = "error", required = false) Boolean error,
                           Model model, HttpServletRequest request) {

        if (Boolean.TRUE.equals(error)) { // as error might be null we have to put this statement in this order
            AuthenticationException authenticationException =
                    (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (authenticationException.getClass().equals(LockedException.class)) {
                model.addAttribute("error", "You have to verify your " +
                        "email before logging onto the page");
            } else {
                model.addAttribute("error", "No user with such credentials was found");
            }
        }
        return "login";
    }

    @GetMapping("/sign_up")
    public String signUpGet() {
        return "signUp";
    }

    @PostMapping("/sign_up")
    public String signUpPost(@RequestParam(name = "email") String email,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "passwordTwo") String passwordTwo,
                             Model model) {

        User user = new User(name, email, password);

        List<UserError> errorList = userValidator.validate(user, passwordTwo); // making our validations

        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "signUp";
        } else {// we want to save user in our database
            userService.save(user);
            return "index"; // todo: change
//            emailSender.sendEmailVerificationEmail(user);
        }

    }


    @GetMapping("/")
    public String test() {

        Date date = new Date(2021070915);


        WashingMachineBooking booking = new WashingMachineBooking(userService.findByEmail("test"),
                washingMachineService.findById(1), date, date);
        washingBookingMachineBookingService.save(booking);

        return "index";
    }


}
