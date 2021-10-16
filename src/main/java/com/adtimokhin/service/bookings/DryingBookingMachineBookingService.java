package com.adtimokhin.service.bookings;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface DryingBookingMachineBookingService {

    List<DryingMachineBooking> getBookings(DryingMachine machine);

    void save(DryingMachineBooking booking);
}
