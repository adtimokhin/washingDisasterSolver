package com.adtimokhin.service.bookings.impl;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.repository.bookings.WashingMachineBookingRepository;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class WashingBookingMachineBookingServiceImpl implements WashingBookingMachineBookingService {

    @Autowired
    private WashingMachineBookingRepository washingMachineBookingRepository;

    @Override
    public List<WashingMachineBooking> getBookings(WashingMachine machine) {
        return washingMachineBookingRepository.findWashingMachineBookingByWashingMachineIdOrderByIdAsc(machine.getId());
    }

    @Override
    public void save(WashingMachineBooking booking) {
        washingMachineBookingRepository.save(booking);
    }
}