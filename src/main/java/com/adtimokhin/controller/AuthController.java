package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.service.UserService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.email.SimpleEmailSender;
import com.adtimokhin.util.token.SimpleEmailTokenGenerator;
import com.adtimokhin.validation.UserValidator;
import com.adtimokhin.validation.errors.UserError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    private SimpleEmailSender emailSender;

    @Autowired
    @Qualifier("washingBookingMachineServiceImpl")
    private WashingBookingMachineService washingMachineService;

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);


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
        } else {
            userService.save(user);
            emailSender.sendEmailVerificationEmail(user);
            return "email/verificationEmailSentConfirmation";
        }

    }


    @GetMapping("/")
    public String index(){
        logger.trace("AHAHHAHAHAHhjh");
        return "index";}


    @GetMapping("/verify/{token}")
    public String verifyEmailPost(@PathVariable(name = "token") String token) {
        User user = userService.findByEmailToken(token);
        if (user != null) { // if email was verified
            userService.removeEmailToken(user);
            return "email/successfulVerification";
        }
        return "email/unsuccessfulVerification";
    }

}
