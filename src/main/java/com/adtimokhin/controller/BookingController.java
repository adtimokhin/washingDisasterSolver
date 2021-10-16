package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.service.UserService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.DateFormatResolver;
import com.adtimokhin.util.TimeTable;
import com.adtimokhin.util.TimeTableContainer;
import com.adtimokhin.validation.BookingValidator;
import com.adtimokhin.validation.errors.BookingError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Controller
@RequestMapping("booking")
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
    private BookingValidator bookingValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private TimeTableContainer timeTableContainer;

    @Autowired
    private ContextProvider contextProvider;


    private final DateFormatResolver dateFormatResolver = new DateFormatResolver();


    @GetMapping("/view/washing_machines")
    public String getAllWashingMachineData(@RequestParam(name = "date", required = false) String date, Model model) {
        // getting information about the time when certain machines are available.
        if(date != null) {
            if (!dateFormatResolver.onTheSameDay(date, timeTableContainer.getDay())) {
                timeTableContainer.changeDay(date);
            }
        }
        List<TimeTable> timeTables = timeTableContainer.getWashingMachineTimeTables();

        model.addAttribute("timeTables", timeTables);
        return "booking/preview";
    }

    @PostMapping("/add/washing_machine")
    public String addBooking(@RequestParam(name = "machineId") String machineId,
                             @RequestParam(name = "startHour") String startHour,
                             @RequestParam(name = "startMinute") String startMinute,
                             @RequestParam(name = "endHour") String endHour,
                             @RequestParam(name = "endMinute") String endMinute,
                             Model model) {

        User user = contextProvider.getUser();

        List<BookingError> errors = bookingValidator.validate(machineId,
                startHour, startMinute, endHour, endMinute, user, true);
        if (errors == null) {
            WashingMachineBooking booking = new WashingMachineBooking(user,
                    washingMachineService.findById(Integer.parseInt(machineId)),
                    dateFormatResolver.resolveTimeForToday(startHour, startMinute),
                    dateFormatResolver.resolveTimeForToday(endHour, endMinute));
            washingBookingMachineBookingService.save(booking);
            return "index";
        }
        model.addAttribute("errors", errors);
        return "booking/preview";
    }

}
