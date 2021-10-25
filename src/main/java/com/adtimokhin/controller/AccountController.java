package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.util.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @Autowired
    private DryingBookingMachineBookingService dryingBookingMachineBookingService;

    @Autowired
    private ContextProvider contextProvider;

    private final Sorter sorter = new Sorter();

    @GetMapping("/")
    public String viewAccountInfo(
            @RequestParam(name = "msg", required = false) String msg,
            Model model){
        User user = contextProvider.getUser();
        List<WashingMachineBooking> washingMachineBookingList = washingBookingMachineBookingService.findAllByUser(user);
        List<DryingMachineBooking> dryingMachineBookingList = dryingBookingMachineBookingService.findAllByUser(user);

        model.addAttribute("name" , user.getName());
        if(washingMachineBookingList != null){
            model.addAttribute("washings" , sorter.sortWashingMachineBooking(sorter.getRelevantWashingBookings(washingMachineBookingList)));
        }
        if(dryingMachineBookingList != null){
            model.addAttribute("dryings" , sorter.sortDryingMachineBookings(sorter.getRelevantDryingBookings(dryingMachineBookingList)));
        }

        if(msg != null){
            model.addAttribute("msg" , msg);
        }

        return "account/account";
    }
}
