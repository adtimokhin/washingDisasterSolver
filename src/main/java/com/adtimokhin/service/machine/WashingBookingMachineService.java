package com.adtimokhin.service.machine;

import com.adtimokhin.model.machine.WashingMachine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface WashingBookingMachineService{

    List<WashingMachine> findAll();

    WashingMachine findById(int id);
}
