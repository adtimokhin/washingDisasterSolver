package com.adtimokhin.repository.bookings;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.WashingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Repository
public interface WashingMachineBookingRepository extends JpaRepository<WashingMachineBooking, Integer> {

    List<WashingMachineBooking> findWashingMachineBookingByWashingMachineIdOrderByIdAsc(int id);

    void deleteById(int id);

}
