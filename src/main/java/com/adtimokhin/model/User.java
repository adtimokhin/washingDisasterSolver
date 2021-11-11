package com.adtimokhin.model;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.complaints.DryingMachineComplaint;
import com.adtimokhin.model.complaints.WashingMachineComplaint;
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
@Table(name = "user_table", schema = "main_schema")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name = "name")
    private String name;

//    @Column(name = "email")
    private String email;

//    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<DryingMachineBooking> dryingMachineBookingList;

    @OneToMany(mappedBy = "user")
    private List<WashingMachineBooking> washingMachineBookingsList;

    @OneToMany(mappedBy = "reportingUser" , targetEntity = WashingMachineComplaint.class)
    private List<WashingMachineComplaint> reportingWashingMachineComplaints;

    @OneToMany(mappedBy = "reportingUser" , targetEntity = DryingMachineComplaint.class)
    private List<DryingMachineComplaint> reportingDryingMachineComplaints;

    @Column(name = "email_token")
    private String emailToken;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
