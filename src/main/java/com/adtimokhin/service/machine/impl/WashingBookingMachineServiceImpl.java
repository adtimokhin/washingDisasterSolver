package com.adtimokhin.service.machine.impl;

import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.repository.machine.WashingMachineRepository;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class WashingBookingMachineServiceImpl implements WashingBookingMachineService {

    @Autowired
    private WashingMachineRepository washingMachineRepository;

    @Override
    public List<WashingMachine> findAll() {
        return washingMachineRepository.findAll();
    }

    @Override
    public WashingMachine findById(int id) {
        return washingMachineRepository.findById(id).get();
    }
}
