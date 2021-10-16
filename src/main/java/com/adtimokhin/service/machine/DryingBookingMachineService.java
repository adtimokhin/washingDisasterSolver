package com.adtimokhin.service.machine;

import com.adtimokhin.model.machine.DryingMachine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface DryingBookingMachineService {

    List<DryingMachine> findAll();

    DryingMachine findById(int id);
}
