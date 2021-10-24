package com.adtimokhin.model.complaints;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 24.10.2021
 **/
@Entity
@Table(name = "drying_machine_complaint_table", schema = "main_schema")
@Getter
@Setter
@NoArgsConstructor
public class DryingMachineComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "reporting_user_id")
    private User reportingUser;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    private DryingMachineBooking booking;

    public DryingMachineComplaint(User reportingUser, DryingMachineBooking booking) {
        this.reportingUser = reportingUser;
        this.booking = booking;
    }
}