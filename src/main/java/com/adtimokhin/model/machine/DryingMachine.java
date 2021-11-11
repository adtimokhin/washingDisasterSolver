package com.adtimokhin.model.machine;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "drying_machine_table", schema = "main_schema")
@Getter
@Setter
@NoArgsConstructor
public class DryingMachine implements Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "dryingMachine", targetEntity = DryingMachineBooking.class)
    private List<DryingMachineBooking> dryingMachineBookingList;

    @Override
    public int getId() {
        return id;
    }

}
