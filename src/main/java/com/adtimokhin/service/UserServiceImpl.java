package com.adtimokhin.service;

import com.adtimokhin.model.User;
import com.adtimokhin.repository.UserRepository;
import com.adtimokhin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
