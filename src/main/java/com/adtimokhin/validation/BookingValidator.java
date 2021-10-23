package com.adtimokhin.validation;

import com.adtimokhin.model.User;
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

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Component
public class BookingValidator {

    @Autowired
    private WashingBookingMachineService washingBookingMachineService;

    @Autowired
    private DryingBookingMachineService dryingBookingMachineService;

    @Autowired
    private TimeTableContainer timeTableContainer;

    private static final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    public List<BookingError> validate(String machineType, String machineId, String startHour, String startMinute, String endHour, String endMinute, String date,User user, boolean isWashingMachine) {
        List<BookingError> errors = new ArrayList<>();
        try {
            int machineIdInt = Integer.parseInt(machineId);
            int startHourInt = Integer.parseInt(startHour);
            int startMinuteInt = Integer.parseInt(startMinute);
            int endHourInt = Integer.parseInt(endHour);
            int endMinuteInt = Integer.parseInt(endMinute);

            if(date == null){
                errors.add(new BookingError("Date is not provided"));
                return errors;
            }

            if(machineType.equals(WASHING_MACHINE_TYPE)){
                if (washingBookingMachineService.findById(machineIdInt) == null) {
                    errors.add(new BookingError("No such machine is found."));
                    return errors;
                }
            }else if(machineType.equals(DRYING_MACHINE_TYPE)){
                if (dryingBookingMachineService.findById(machineIdInt) == null) {
                    errors.add(new BookingError("No such machine is found."));
                    return errors;
                }
            }else {
                errors.add(new BookingError("You have to choose a valid machine type!"));
                return errors;
            }


            if (dateFormatResolver.isTimeBigger(startHourInt, startMinuteInt, endHourInt, endMinuteInt)){
                errors.add(new BookingError("Starting time should come before the finishing time."));
                return errors;
            }

            if(dateFormatResolver.isDateBeforeAnother(date, dateFormatResolver.today())){
                errors.add(new BookingError("Booking is made for time that had already passed!"));
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
