package com.adtimokhin.service;

import com.adtimokhin.aspect.NotEmptyArguments;
import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface UserService {

    User findByEmail(String email);

    @Transactional
    @NotEmptyArguments
    void save(User user);

    List<String> findAllEmailTokens();

    @Transactional
    void removeEmailToken(User user);

    User findByEmailToken(String token);
}
