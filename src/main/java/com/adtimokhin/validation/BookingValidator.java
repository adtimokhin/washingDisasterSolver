package com.adtimokhin.validation;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.time.DateFormatResolver;
import com.adtimokhin.util.time.TimeTable;
import com.adtimokhin.util.time.TimeTableContainer;
import com.adtimokhin.validation.errors.BookingError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.adtimokhin.controller.BookingController.DRYING_MACHINE_TYPE;
import static com.adtimokhin.controller.BookingController.WASHING_MACHINE_TYPE;
import static com.adtimokhin.util.time.DateFormatResolver.MAX_LENGTH_FOR_BOOKING;
import static com.adtimokhin.util.time.DateFormatResolver.MINUTES_BEFORE_CANCEL;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Component
public class BookingValidator {

    @Autowired
    private WashingBookingMachineService washingBookingMachineService;

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @Autowired
    private DryingBookingMachineService dryingBookingMachineService;

    @Autowired
    private DryingBookingMachineBookingService dryingBookingMachineBookingService;

    @Autowired
    private TimeTableContainer timeTableContainer;

    public final static int MAX_BOOKINGS = 3;

    private static final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    public List<BookingError> validate(String machineType, String machineId, String startHour, String startMinute, String endHour, String endMinute, String date, User user, boolean isWashingMachine) {
        List<BookingError> errors = new ArrayList<>();
        try {
            int machineIdInt = Integer.parseInt(machineId);
            int startHourInt = Integer.parseInt(startHour);
            int startMinuteInt = Integer.parseInt(startMinute);
            int endHourInt = Integer.parseInt(endHour);
            int endMinuteInt = Integer.parseInt(endMinute);

            if (date == null) {
                errors.add(new BookingError("Date is not provided."));
                return errors;
            } else if (date.equals("")) {
                errors.add(new BookingError("Date is not provided."));
                return errors;
            }

            if (machineType.equals(WASHING_MACHINE_TYPE)) {
                if (washingBookingMachineService.findById(machineIdInt) == null) {
                    errors.add(new BookingError("No such machine is found."));
                    return errors;
                }
            } else if (machineType.equals(DRYING_MACHINE_TYPE)) {
                if (dryingBookingMachineService.findById(machineIdInt) == null) {
                    errors.add(new BookingError("No such machine is found."));
                    return errors;
                }
            } else {
                errors.add(new BookingError("You have to choose a valid machine type."));
                return errors;
            }

            // checking if hours and minutes are in right range
            if ((startHourInt < 0 || startHourInt > 23) || endHourInt < 0 || endHourInt > 23) {
                errors.add(new BookingError("Hours should be in range 0-23."));
                return errors;
            }
            if ((startMinuteInt < 0 || startMinuteInt > 59) || (endMinuteInt < 0 || endMinuteInt > 59)) {
                errors.add(new BookingError("Minutes should be in range 0-59."));
                return errors;
            }


            if (dateFormatResolver.isTimeBigger(startHourInt, startMinuteInt, endHourInt, endMinuteInt)) {
                errors.add(new BookingError("Starting time should come before the finishing time."));
                return errors;
            }

            if (dateFormatResolver.isDateBeforeAnother(date, dateFormatResolver.today())) {
                errors.add(new BookingError("Booking is made for time that had already passed."));
                return errors;
            }

            // check if spot if free
            TimeTable timeTable = timeTableContainer.getTimeTable(machineIdInt, isWashingMachine);
            if (!timeTable.isFreeBetween(startHourInt, startMinuteInt, endHourInt, endMinuteInt)) {
                errors.add(new BookingError("This time slot is not available."));
                return errors;
            }
            String endDate = dateFormatResolver.resolveTimeForDate(endHour, endMinute, "0000 00 00 00:00");
            String startDate = dateFormatResolver.resolveTimeForDate(startHour, startMinute, "0000 00 00 00:00");
            if (dateFormatResolver.areFarEnoughInTime(endDate, startDate, MAX_LENGTH_FOR_BOOKING)) {
                errors.add(new BookingError("Bookings can be made for " + MAX_LENGTH_FOR_BOOKING + " minutes at most."));
                return errors;
            }

            if (user == null) {
                errors.add(new BookingError("Unidentified user."));
                return errors;
            }

            // check how many bookings user as for this day
            if (isWashingMachine) {
                List<WashingMachineBooking> bookings = (washingBookingMachineBookingService.findAllWashingMachineBookings(user.getId(), date));
                if(bookings != null){
                    if(bookings.size() >= MAX_BOOKINGS){
                        errors.add(new BookingError("You can make maximum of " + MAX_BOOKINGS + " bookings per day."));
                        return errors;
                    }
                }
            }else {
                List<DryingMachineBooking> bookings = (dryingBookingMachineBookingService.findAllDryingMachineBookings(user.getId(), date));
                if(bookings != null){
                    if(bookings.size() >= MAX_BOOKINGS){
                        errors.add(new BookingError("You can make maximum of " + MAX_BOOKINGS + " bookings per day."));
                        return errors;
                    }
                }
            }

            // return null if all validations have passed
            return null;

        } catch (Exception e) {
            errors.add(new BookingError("Fill in all of the fields as numbers."));
            return errors;
        }
    }

}
