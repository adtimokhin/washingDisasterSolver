package com.adtimokhin.security;

import com.adtimokhin.controller.DefaultExceptionHandlerControlAdvice;
import com.adtimokhin.exceptions.NoSuchUserFoundException;
import com.adtimokhin.model.User;
import com.adtimokhin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 11.08.2021
 **/

@Component
public class ContextProvider {

    @Autowired
    private UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(ContextProvider.class);

    public User getUser() {
                SecurityContext context = SecurityContextHolder.getContext(); // securityContext contains security
        // information about a thread that a current User uses
        Authentication authentication = context.getAuthentication(); // getting authentication token from the securityContext
        String principle = authentication.getPrincipal().toString(); // getting email from the authentication token

        User user = userService.findByEmail(principle);

        if (user == null){ // checking if user is equal to null
            logger.error("No user is associated with currently running thread.");
            throw new NoSuchUserFoundException();
        }
        return user;
    }

}







