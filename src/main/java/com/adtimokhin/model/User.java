package com.adtimokhin.model;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.complaints.DryingMachineComplaint;
import com.adtimokhin.model.complaints.WashingMachineComplaint;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "user_table", schema = "main_schema")
//@Getter
//@Setter
//@NoArgsConstructor
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
    @Setter
    @Getter
    private List<WashingMachineComplaint> reportingWashingMachineComplaints;

    @OneToMany(mappedBy = "reportingUser" , targetEntity = DryingMachineComplaint.class)
    @Setter
    @Getter
    private List<DryingMachineComplaint> reportingDryingMachineComplaints;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DryingMachineBooking> getDryingMachineBookingList() {
        return dryingMachineBookingList;
    }

    public void setDryingMachineBookingList(List<DryingMachineBooking> dryingMachineBookingList) {
        this.dryingMachineBookingList = dryingMachineBookingList;
    }

    public List<WashingMachineBooking> getWashingMachineBookingsList() {
        return washingMachineBookingsList;
    }

    public void setWashingMachineBookingsList(List<WashingMachineBooking> washingMachineBookingsList) {
        this.washingMachineBookingsList = washingMachineBookingsList;
    }
}
