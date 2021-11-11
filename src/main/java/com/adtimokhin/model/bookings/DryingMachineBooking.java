package com.adtimokhin.model.bookings;

import com.adtimokhin.model.complaints.DryingMachineComplaint;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.User;
import com.adtimokhin.model.machine.WashingMachine;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "drying_machine_bookings_table", schema = "main_schema")
@NoArgsConstructor
@Getter
@Setter
public class DryingMachineBooking implements Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "drying_machine_id")
    private DryingMachine dryingMachine;

    @Column(name = "start_time")
    private String startDate;

    @Column(name = "end_time")
    private String endDate;

    @OneToMany(mappedBy = "booking")
    private List<DryingMachineComplaint> complaintList;

    public DryingMachineBooking(User user, DryingMachine dryingMachine, String startDate, String endDate) {
        this.user = user;
        this.dryingMachine = dryingMachine;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DryingMachineBooking{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", dryingMachineId=" + dryingMachine.getId() +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public String presentNicely() {
        return "Booking for a drying machine with id " + dryingMachine.getId() + " made to start from " + startDate + " and finish at " + endDate;
    }
}
