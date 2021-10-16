package com.adtimokhin.security;

import com.adtimokhin.model.User;
import com.adtimokhin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author adtimokhin
 * 10.08.2021
 **/

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName(); // we get the username parameter
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("User with such email was not detected");
        }

        String password = authentication.getCredentials().toString(); // we get the credentials that we will turn into string

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(user.getRole());
        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
