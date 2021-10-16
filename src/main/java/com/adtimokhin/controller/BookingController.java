package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.UserService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Controller
public class BookingController {

    @Qualifier("washingBookingMachineServiceImpl")
    @Autowired
    private WashingBookingMachineService washingMachineService;

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @Qualifier("dryingBookingMachineServiceImpl")
    @Autowired
    private DryingBookingMachineService dryingMachineService;

    @Autowired
    private UserService userService;


    @GetMapping("/view/washing_machines")
    public String getAllWashingMachineData(Model model) {
        // getting information about the time when certain machines are available.
        List<WashingMachine> washingMachineList = washingMachineService.findAll();

        List<TimeTable> timeTables = new ArrayList<>();
        for (WashingMachine washingMachine :
                washingMachineList) {
            TimeTable timeTable = new TimeTable();
            timeTable.generateTimeTable(washingMachine, washingBookingMachineBookingService);
            timeTables.add(timeTable);
        }
        model.addAttribute("timeTables", timeTables);
        return "booking/preview";
    }

    @PostMapping("/add/booking")
    public String addBooking(@RequestParam(name = "machineId") String machineId,
                             @RequestParam(name = "startHour") String startHour,
                             @RequestParam(name = "startMinute") String startMinute,
                             @RequestParam(name = "endHour") String endHour,
                             @RequestParam(name = "endMinute") String endMinute) {
        User user = userService.findByEmail("test"); // todo: change
        WashingMachine washingMachine = washingMachineService.findById(machineId);
        WashingMachineBooking washingMachineBooking = new WashingMachineBooking(user);

    }

}
