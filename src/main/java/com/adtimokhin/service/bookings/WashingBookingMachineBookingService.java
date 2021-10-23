package com.adtimokhin.service.bookings;

import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface WashingBookingMachineBookingService {

    List<WashingMachineBooking> getBookings(WashingMachine machine);

    @Transactional
    void save(WashingMachineBooking booking);

    @Transactional
    void delete(WashingMachineBooking booking);

    WashingMachineBooking findById(int id);
}
