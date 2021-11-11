package com.adtimokhin.model.bookings;

import com.adtimokhin.model.User;
import com.adtimokhin.model.complaints.WashingMachineComplaint;
import com.adtimokhin.model.machine.WashingMachine;
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
@Table(name = "washing_machine_bookings_table", schema = "main_schema")
@NoArgsConstructor
@Getter
@Setter
public class WashingMachineBooking implements Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "washing_machine_id")
    private WashingMachine washingMachine;

    @Column(name = "start_time")
    private String startDate;

    @Column(name = "end_time")
    private String endDate;

    @OneToMany(mappedBy = "booking")
    private List<WashingMachineComplaint> washingMachineComplaints;

    public WashingMachineBooking(User user, WashingMachine washingMachine, String startDate, String endDate) {
        this.user = user;
        this.washingMachine = washingMachine;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "WashingMachineBooking{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", washingMachineId=" + washingMachine.getId() +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public String presentNicely() {
        return "Booking for a washing machine with id " + washingMachine.getId() + " made to start from " + startDate + " and finish at " + endDate;

    }
}
