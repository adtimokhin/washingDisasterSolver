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
    public String addComplaintProcess(@RequestParam(name = "type") String type,
                                      @RequestParam(name = "id") int id, Model model) {
        if(id < 0 || id > 10){ // todo: we need to calculate this max. value somehow else for the flexibility sake. But we won't do that right now.
            model.addAttribute("msg" , "id in wrong range.");
            return "complaints/complaint";
        }

        if (type.equals("W")) {
            WashingMachineBooking booking = washingBookingMachineBookingService.getBookingForMachineWithId(id, dateFormatResolver.today());
            if (booking == null) {
                model.addAttribute("msg", "Right now this machine is not booked or this is the first booking on a day. This system is currently is in no power to deal with this. Please, call someone to help.");
                return "complaints/complaint";
            }


            User user = contextProvider.getUser();
            WashingMachineComplaint complaint = new WashingMachineComplaint(user, booking);


            washingMachineComplaintService.save(complaint);

        } else if (type.equals("D")) {
            DryingMachineBooking booking = dryingBookingMachineBookingService.getBookingForMachineWithId(id, dateFormatResolver.today());
            if (booking == null) {
                model.addAttribute("msg", "Right now this machine is not booked. You are in right to ");
                return "complaints/complaint";
            }


            User user = contextProvider.getUser();
            DryingMachineComplaint complaint = new DryingMachineComplaint(user, booking);

            dryingMachineComplaintService.save(complaint);
        }
        return "index"; // todo: add redirect success pages
    }

    @GetMapping("/")
    public String viewAllComplaints(Model model) {
        List<WashingMachineComplaint> washingMachineComplaints = washingMachineComplaintService.findAll();
        List<DryingMachineComplaint> dryingMachineComplaints = dryingMachineComplaintService.findAll();

        if (washingMachineComplaints != null) {
            model.addAttribute("washingMachineComplaints", washingMachineComplaints);
        }
        if (dryingMachineComplaints != null) {
            model.addAttribute("dryingMachineComplaints", dryingMachineComplaints);
        }

        return "complaints/complaint";
    }
}
