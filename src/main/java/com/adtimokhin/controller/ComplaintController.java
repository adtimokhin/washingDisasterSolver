package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.complaints.DryingMachineComplaint;
import com.adtimokhin.model.complaints.WashingMachineComplaint;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.complaints.DryingMachineComplaintService;
import com.adtimokhin.service.complaints.WashingMachineComplaintService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.util.time.DateFormatResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

@Controller
@RequestMapping("complaint")
public class ComplaintController {

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @Autowired
    private WashingMachineComplaintService washingMachineComplaintService;

    @Autowired
    private DryingBookingMachineBookingService dryingBookingMachineBookingService;

    @Autowired
    private DryingMachineComplaintService dryingMachineComplaintService;

    @Autowired
    private ContextProvider contextProvider;

    private final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    @PostMapping("/add")
    public String addComplaintProcess(@RequestParam(name = "isWashingMachine") String isWashingMachine,
                                      @RequestParam(name ="id") int id, Model model){
        if (isWashingMachine.equals("yes")){
            WashingMachineBooking booking = washingBookingMachineBookingService.getBookingForMachineWithId(id, dateFormatResolver.today());
            if(booking == null){
                // todo: Объявить отправившего жалобу лгуном.
                model.addAttribute("msg" , "Right now this machine is not booked. You are in right to ");
                return "complaints/complaint";
            }


            User user = contextProvider.getUser();
            WashingMachineComplaint complaint = new WashingMachineComplaint(user, booking);


            washingMachineComplaintService.save(complaint);

        }
        else if(isWashingMachine.equals("no")){
            DryingMachineBooking booking= dryingBookingMachineBookingService.getBookingForMachineWithId(id, dateFormatResolver.today());
            if(booking == null){
                // todo: Объявить отправившего жалобу лгуном.
                model.addAttribute("msg" , "Right now this machine is not booked. You are in right to ");
                return "complaints/complaint";
            }


            User user = contextProvider.getUser();
            DryingMachineComplaint complaint = new DryingMachineComplaint(user, booking);


            dryingMachineComplaintService.save(complaint);
        }
        return "index";
    }

    @GetMapping("/")
    public String viewAllComplaints(Model model){
        List<WashingMachineComplaint> washingMachineComplaints = washingMachineComplaintService.findAll();
        List<DryingMachineComplaint> dryingMachineComplaints = dryingMachineComplaintService.findAll();

        if(washingMachineComplaints != null) {
            model.addAttribute("washingMachineComplaints", washingMachineComplaints);
        }
        if (dryingMachineComplaints != null){
            model.addAttribute("dryingMachineComplaints" ,dryingMachineComplaints);
        }

        return "complaints/complaint";
    }
}
