package com.adtimokhin.service.bookings.impl;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.repository.bookings.WashingMachineBookingRepository;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.util.time.TimeTableContainer;
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
    private TimeTableContainer timeTableContainer;

    @Autowired
    private WashingMachineBookingRepository washingMachineBookingRepository;

    @Override
    public List<WashingMachineBooking> getBookings(WashingMachine machine) {
        return washingMachineBookingRepository.findWashingMachineBookingByWashingMachineIdOrderByIdAsc(machine.getId());
    }

    @Override
    public void save(WashingMachineBooking booking) {
        washingMachineBookingRepository.save(booking);

        timeTableContainer.updateTimeTable(booking.getWashingMachine().getId(), true);
    }

    @Override
    public void delete(WashingMachineBooking booking) {
        washingMachineBookingRepository.delete(booking);
    }

    @Override
    public WashingMachineBooking findById(int id) {
        return washingMachineBookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<WashingMachineBooking> findAllByUser(User user) {
        return washingMachineBookingRepository.findAllByUserIdOrderByIdAsc(user.getId());
    }

}
