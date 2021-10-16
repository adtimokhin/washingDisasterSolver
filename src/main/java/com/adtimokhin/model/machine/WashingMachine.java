package com.adtimokhin.model.machine;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "washing_machine_table")
@Getter
public class WashingMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "washingMachine", targetEntity = WashingMachineBooking.class, fetch = FetchType.EAGER)
    private List<WashingMachineBooking> washingMachineBookingList;
}
