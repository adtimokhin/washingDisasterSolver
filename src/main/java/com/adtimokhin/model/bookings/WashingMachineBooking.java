package com.adtimokhin.model.bookings;

import com.adtimokhin.model.User;
import com.adtimokhin.model.machine.WashingMachine;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "washing_machine_bookings_table", schema = "main_schema")
@Data
@NoArgsConstructor
public class WashingMachineBooking {


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

    public WashingMachineBooking(User user, WashingMachine washingMachine, String startDate, String endDate) {
        this.user = user;
        this.washingMachine = washingMachine;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
