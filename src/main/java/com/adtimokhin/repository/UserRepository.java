package com.adtimokhin.repository;

import com.adtimokhin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findAllByEmailTokenIsNotNull();

    User findByEmailToken(String token);

}
