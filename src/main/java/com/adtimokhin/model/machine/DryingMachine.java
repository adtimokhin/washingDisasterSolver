package com.adtimokhin.model.machine;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "drying_machine_table")
@Getter
public class DryingMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "dryingMachine", targetEntity = DryingMachineBooking.class, fetch = FetchType.EAGER)
    private List<DryingMachineBooking> dryingMachineBookingList;
}
