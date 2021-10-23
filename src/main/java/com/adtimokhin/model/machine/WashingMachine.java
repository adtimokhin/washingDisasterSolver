package com.adtimokhin.model.machine;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "washing_machine_table", schema = "main_schema")
//@Getter
//@Setter
public class WashingMachine implements Machine{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "washingMachine", targetEntity = WashingMachineBooking.class)
    private List<WashingMachineBooking> washingMachineBookingList;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WashingMachineBooking> getWashingMachineBookingList() {
        return washingMachineBookingList;
    }

    public void setWashingMachineBookingList(List<WashingMachineBooking> washingMachineBookingList) {
        this.washingMachineBookingList = washingMachineBookingList;
    }

    public WashingMachine() {
    }
}
