package com.adtimokhin.controller;

import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Controller
public class BookingController {

    @Autowired
    private WashingBookingMachineService washingMachineService;

    @Autowired
    private DryingBookingMachineService dryingMachineService;


    @GetMapping("/view/washing_machines")
    public String getAllWashingMachineData(Model model){
        // getting information about the time when a certain machine is available next.
        return "booking/preview";
    }

}
