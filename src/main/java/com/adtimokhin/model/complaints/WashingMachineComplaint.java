package com.adtimokhin.model.complaints;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

@Entity
@Table(name = "washing_machine_complaint_table", schema = "main_schema")
@Getter
@Setter
@NoArgsConstructor
public class WashingMachineComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "reporting_user_id")
    private User reportingUser;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private WashingMachineBooking booking;


    public WashingMachineComplaint(User reportingUser, WashingMachineBooking booking) {
        this.reportingUser = reportingUser;
        this.booking = booking;
    }
}
