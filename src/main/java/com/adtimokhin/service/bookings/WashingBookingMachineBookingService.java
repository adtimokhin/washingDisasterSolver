package com.adtimokhin.service.bookings;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.WashingMachine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface WashingBookingMachineBookingService {

    List<WashingMachineBooking> getBookings(WashingMachine machine);

    void save(WashingMachineBooking booking);
}
