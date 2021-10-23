package com.adtimokhin.service;

import com.adtimokhin.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface UserService {

    User findByEmail(String email);

    @Transactional
    void save(User user);
}
