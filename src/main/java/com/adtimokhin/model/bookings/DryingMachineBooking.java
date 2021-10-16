package com.adtimokhin.model.bookings;

import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Entity
@Table(name = "drying_machine_bookings_table", schema = "main_schema")
@Data
public class DryingMachineBooking {

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

}
