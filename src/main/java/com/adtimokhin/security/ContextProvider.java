package com.adtimokhin.security;

import com.adtimokhin.exceptions.NoSuchUserFoundException;
import com.adtimokhin.model.User;
import com.adtimokhin.service.UserService;
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

    public User getUser() {
                SecurityContext context = SecurityContextHolder.getContext(); // securityContext contains security
        // information about a thread that a current User uses
        Authentication authentication = context.getAuthentication(); // getting authentication token from the securityContext
        String principle = authentication.getPrincipal().toString(); // getting email from the authentication token

        User user = userService.findByEmail(principle);

        if (user == null){ // checking if user is equal to null
            throw new NoSuchUserFoundException();
        }
        return user;
    }

}







