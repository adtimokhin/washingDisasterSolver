package com.adtimokhin.repository.bookings;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Repository
public interface DryingMachineBookingRepository extends JpaRepository<DryingMachineBooking, Integer> {

    List<DryingMachineBooking> findDryingMachineBookingsByDryingMachineIdOrderByIdAsc(int id);


}
