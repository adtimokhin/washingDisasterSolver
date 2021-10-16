package com.adtimokhin.service.bookings.impl;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.repository.bookings.DryingMachineBookingRepository;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class DryingBookingMachineBookingServiceImpl implements DryingBookingMachineBookingService {

    @Autowired
    private DryingMachineBookingRepository dryingMachineBookingRepository;

    @Override
    public List<DryingMachineBooking> getBookings(DryingMachine machine) {
        return dryingMachineBookingRepository.findDryingMachineBookingsByDryingMachineIdOrderByIdAsc(machine.getId());
    }

    @Override
    public void save(DryingMachineBooking booking) {
        dryingMachineBookingRepository.save(booking);
    }
}
