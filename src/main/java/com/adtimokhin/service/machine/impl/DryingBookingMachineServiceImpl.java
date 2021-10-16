package com.adtimokhin.service.machine.impl;

import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.repository.machine.DryingMachineRepository;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class DryingBookingMachineServiceImpl implements DryingBookingMachineService {

    @Autowired
    private DryingMachineRepository dryingMachineRepository;


    @Override
    public List<DryingMachine> findAll() {
        return dryingMachineRepository.findAll();
    }
}
