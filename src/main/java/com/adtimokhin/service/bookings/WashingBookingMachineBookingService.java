package com.adtimokhin.service.bookings;

import com.adtimokhin.aspect.NotEmptyArguments;
import com.adtimokhin.model.User;
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

    @NotEmptyArguments
    List<WashingMachineBooking> getBookings(WashingMachine machine);

    @Transactional
    @NotEmptyArguments
    void save(WashingMachineBooking booking);

    @Transactional
    @NotEmptyArguments
    void delete(WashingMachineBooking booking);

    WashingMachineBooking findById(int id);

    @NotEmptyArguments
    List<WashingMachineBooking> findAllByUser(User user);

    List<WashingMachineBooking> findAllByWashingMachineId(int id);

    WashingMachineBooking getBookingForMachineWithId(int id, String date);

    List<WashingMachineBooking> findAllWashingMachineBookings(int userId, String date);

}
