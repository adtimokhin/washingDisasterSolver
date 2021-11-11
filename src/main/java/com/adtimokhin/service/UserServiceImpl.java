package com.adtimokhin.service;

import com.adtimokhin.aspect.NotEmptyArguments;
import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.repository.UserRepository;
import com.adtimokhin.repository.machine.DryingMachineRepository;
import com.adtimokhin.repository.machine.WashingMachineRepository;
import com.adtimokhin.service.UserService;
import com.adtimokhin.util.token.SimpleEmailTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SimpleEmailTokenGenerator simpleEmailTokenGenerator;


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailToken(simpleEmailTokenGenerator.generate());
        userRepository.save(user);
    }

    @Override
    public List<String> findAllEmailTokens() {
        List<User> users = userRepository.findAllByEmailTokenIsNotNull();
        return users.stream().map(User::getEmailToken).collect(Collectors.toCollection(() -> new ArrayList<>(users.size())));
    }

    @Override
    public void removeEmailToken(User user) {
        user.setEmailToken(null);
        userRepository.save(user);
    }

    @Override
    public User findByEmailToken(String token) {
        return userRepository.findByEmailToken(token);
    }
}
