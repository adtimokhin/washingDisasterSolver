package com.adtimokhin.controller;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.service.UserService;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.time.DateFormatResolver;
import com.adtimokhin.util.time.TimeTable;
import com.adtimokhin.util.time.TimeTableContainer;
import com.adtimokhin.validation.BookingValidator;
import com.adtimokhin.validation.errors.BookingError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adtimokhin.util.time.DateFormatResolver.MINUTES_BEFORE_CANCEL;


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
    private DryingBookingMachineBookingService dryingBookingMachineBookingService;

    @Autowired
    private BookingValidator bookingValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private TimeTableContainer timeTableContainer;

    @Autowired
    private ContextProvider contextProvider;


    private final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    public static final String WASHING_MACHINE_TYPE = "washing_machines";
    public static final String DRYING_MACHINE_TYPE = "drying_machines";


    @GetMapping("/view/{type}")
    public String getAllWashingMachineData(@PathVariable(value = "type", required = false) String machineType,
                                           @RequestParam(name = "date", required = false) String date,
                                           @RequestParam(name = "error", required = false) String error,
                                           Model model) {
        // getting information about the time when certain machines are available.

//        washingBookingMachineBookingService.delete(washingBookingMachineBookingService.findById(82));

        if (date != null) {
            if (!dateFormatResolver.onTheSameDay(date, timeTableContainer.getDay())) {
                timeTableContainer.changeDay(date);
            }
            model.addAttribute("date", date);
        } else {
            model.addAttribute("date", dateFormatResolver.today());
        }
        List<TimeTable> timeTables;
        if (WASHING_MACHINE_TYPE.equals(machineType)) {
            timeTables = timeTableContainer.getWashingMachineTimeTables();
        } else if (DRYING_MACHINE_TYPE.equals(machineType)) {
            timeTables = timeTableContainer.getDryingMachineTimeTables();
        } else {
            model.addAttribute("error", "Please, select type of machinery to use!");
            return "booking/preview";
        }

        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("machineType", machineType);
        model.addAttribute("timeTables", timeTables);
        return "booking/preview";
    }

    @PostMapping("/add/{type}")
    public String addBooking(@PathVariable(value = "type", required = false) String machineType,
                             @RequestParam(name = "machineId") String machineId,
                             @RequestParam(name = "startHour") String startHour,
                             @RequestParam(name = "startMinute") String startMinute,
                             @RequestParam(name = "endHour") String endHour,
                             @RequestParam(name = "endMinute") String endMinute,
                             @RequestParam(name = "date") String date,
                             Model model) {

        User user = contextProvider.getUser();

        boolean isWashingMachine = true;
        if (machineType == null) {
            model.addAttribute("error", "You have to specify machine type!");
            return "redirect:/booking/view/error";
        } else if (DRYING_MACHINE_TYPE.equals(machineType)) {
            isWashingMachine = false;
        } else if (!WASHING_MACHINE_TYPE.equals(machineType)) {
            model.addAttribute("error", "You have to specify machine type!");
            return "redirect:/booking/view/error";
        }

        List<BookingError> errors = bookingValidator.validate(machineType, machineId, startHour, startMinute,
                endHour, endMinute, dateFormatResolver.resolveTimeForDate(startHour, startMinute, date), user,
                isWashingMachine);

        if (errors == null) {
            if (machineType.equals(WASHING_MACHINE_TYPE)) {
                WashingMachineBooking booking = new WashingMachineBooking(user,
                        washingMachineService.findById(Integer.parseInt(machineId)),
                        dateFormatResolver.resolveTimeForDate(startHour, startMinute, date),
                        dateFormatResolver.resolveTimeForDate(endHour, endMinute, date));
                washingBookingMachineBookingService.save(booking);
            } else {
                DryingMachineBooking booking = new DryingMachineBooking(user,
                        dryingMachineService.findById(Integer.parseInt(machineId)),
                        dateFormatResolver.resolveTimeForDate(startHour, startMinute, date),
                        dateFormatResolver.resolveTimeForDate(endHour, endMinute, date));
                dryingBookingMachineBookingService.save(booking);
            }

            return "index";
        }
        model.addAttribute("error", errors.get(0).getMessage());
        model.addAttribute("date", date);

        return "redirect:/booking/view/" + machineType;
    }


    @PostMapping("/cancel/{type}")
    public String cancelBooking(@PathVariable(value = "type", required = false) String machineType,
                                @RequestParam(name = "id") int id, Model model) {

        if (machineType == null) {
            model.addAttribute("msg", "You have to specify machine type!");
            return "redirect:/account/";
        } else if (DRYING_MACHINE_TYPE.equals(machineType)) {
            DryingMachineBooking booking = dryingBookingMachineBookingService.findById(id);
            if (booking != null) {
                if (dateFormatResolver.areFarEnoughInTime(booking.getStartDate(), dateFormatResolver.today(), MINUTES_BEFORE_CANCEL)) {
                    dryingBookingMachineBookingService.delete(booking);
                    model.addAttribute("msg", "Cancellation was successful!");
                    return "redirect:/account/";
                }
            }
        } else if (WASHING_MACHINE_TYPE.equals(machineType)) {
            WashingMachineBooking booking = washingBookingMachineBookingService.findById(id);
            if (booking != null) {
                if (dateFormatResolver.areFarEnoughInTime(booking.getStartDate(), dateFormatResolver.today(), MINUTES_BEFORE_CANCEL)) {
                    washingBookingMachineBookingService.delete(booking);
                    model.addAttribute("msg", "Cancellation was successful!");
                    return "redirect:/account/";
                }
            }
        }

        model.addAttribute("msg", "Could not cancel. You have to cancel " + MINUTES_BEFORE_CANCEL + " minutes before your booking time");
        return "redirect:/account/";


    }

}
