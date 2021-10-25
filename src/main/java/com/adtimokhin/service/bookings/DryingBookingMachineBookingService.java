package com.adtimokhin.service.bookings;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Service
public interface DryingBookingMachineBookingService {

    List<DryingMachineBooking> getBookings(DryingMachine machine);

    @Transactional
    void save(DryingMachineBooking booking);

    @Transactional
    void delete(DryingMachineBooking booking);

    List<DryingMachineBooking> findAllByUser(User user);

    List<DryingMachineBooking> findAllByDryingMachineId(int id);

    DryingMachineBooking findById(int id);

    DryingMachineBooking getBookingForMachineWithId(int id, String date);

}
