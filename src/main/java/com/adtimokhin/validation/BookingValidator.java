package com.adtimokhin.validation;

import com.adtimokhin.model.User;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.DateFormatResolver;
import com.adtimokhin.util.TimeTable;
import com.adtimokhin.util.TimeTableContainer;
import com.adtimokhin.validation.errors.BookingError;
import com.adtimokhin.validation.errors.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Component
public class BookingValidator {

    @Autowired
    private WashingBookingMachineService washingBookingMachineService;

    @Autowired
    private TimeTableContainer timeTableContainer;

    private static final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    public List<BookingError> validate(String machineId, String startHour, String startMinute, String endHour, String endMinute, User user, boolean isWashingMachine) {
        List<BookingError> errors = new ArrayList<>();
        try {
            int machineIdInt = Integer.parseInt(machineId);
            int startHourInt = Integer.parseInt(startHour);
            int startMinuteInt = Integer.parseInt(startMinute);
            int endHourInt = Integer.parseInt(endHour);
            int endMinuteInt = Integer.parseInt(endMinute);

            if (washingBookingMachineService.findById(machineIdInt) == null) {
                errors.add(new BookingError("No such machine is found."));
                return errors;
            }

            if (dateFormatResolver.isTimeBigger(startHourInt, startMinuteInt, endHourInt, endMinuteInt)){
                errors.add(new BookingError("Starting time comes before than finishing time."));
                return errors;
            }

            // check if spot if free
            TimeTable timeTable = timeTableContainer.getTimeTable(machineIdInt, isWashingMachine);
            if(!timeTable.isFreeBetween(startHourInt, startMinuteInt, endHourInt, endMinuteInt)){
                errors.add(new BookingError("This time slot is not available"));return errors;
            }

            if (user == null) {
                errors.add(new BookingError("Unidentified user."));
                return errors;
            }

            // return null if all validations have passed
            return null;

        } catch (Exception e) {
            errors.add(new BookingError("Couldn't parse input into int."));
            return errors;
        }
    }

}
