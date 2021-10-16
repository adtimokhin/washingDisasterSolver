package com.adtimokhin.util;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * @author adtimokhin
 * 16.10.2021
 **/

public class TimeTable {


    @Autowired
    private WashingBookingMachineBookingService washingMachineBookingService;

    @Autowired
    private DryingBookingMachineBookingService dryingMachineBookingService;

    @Getter
    private List<TimePeriod> timePeriods;

    void generateTimeTable(WashingMachine machine) {

        List<WashingMachineBooking> washingMachineBookings = washingMachineBookingService.getBookings(machine);

        int washingMachineBookingsSize = washingMachineBookings.size();

        timePeriods = new ArrayList<>();
        for (int i = 0; i < washingMachineBookingsSize; i++) {
            WashingMachineBooking current = washingMachineBookings.get(i);
            // create TimePeriod for current Booking
            Date startDate = current.getStartDate();
            Date endDate = current.getEndDate();
            timePeriods.add(new TimePeriod(startDate.getHours(), startDate.getMinutes(), endDate.getHours(), endDate.getMinutes(), false));

            if (i == washingMachineBookingsSize - 1) {
                continue;
            }

            WashingMachineBooking next = washingMachineBookings.get(i + 1);
            // comparing two booking dates
            Date nextStartDate = next.getStartDate();
            if (!datesMatch(nextStartDate, endDate)) {
                // create new a free slot in between
                timePeriods.add(new TimePeriod(
                        endDate.getHours(), endDate.getMinutes(), nextStartDate.getHours(), nextStartDate.getMinutes(), true
                ));
            }
        }
    }

    void generateTimeTable(DryingMachine machine) {

        List<DryingMachineBooking> dryingMachineBookings = dryingMachineBookingService.getBookings(machine);

        int washingMachineBookingsSize = dryingMachineBookings.size();

        timePeriods = new ArrayList<>();
        for (int i = 0; i < washingMachineBookingsSize; i++) {
            DryingMachineBooking current = dryingMachineBookings.get(i);
            // create TimePeriod for current Booking
            Date startDate = current.getStartDate();
            Date endDate = current.getEndDate();
            timePeriods.add(new TimePeriod(startDate.getHours(), startDate.getMinutes(), endDate.getHours(), endDate.getMinutes(), false));

            if (i == washingMachineBookingsSize - 1) {
                continue;
            }

            DryingMachineBooking next = dryingMachineBookings.get(i + 1);
            // comparing two booking dates
            Date nextStartDate = next.getStartDate();
            if (!datesMatch(nextStartDate, endDate)) {
                // create new a free slot in between
                timePeriods.add(new TimePeriod(
                        endDate.getHours(), endDate.getMinutes(), nextStartDate.getHours(), nextStartDate.getMinutes(), true
                ));
            }
        }
    }

    private boolean datesMatch(Date start, Date end) {
        if (start.getMinutes() == end.getMinutes()) {
            return start.getHours() == end.getHours();
        }
        return false;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "timePeriods=" + timePeriods +
                '}';
    }

    private static class TimePeriod {
        int startHour;
        int startMinute;
        int endHour;
        int endMinute;
        boolean isFree;

        public TimePeriod(int startHour, int startMinute, int endHour, int endMinute, boolean isFree) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
            this.isFree = isFree;
        }

        @Override
        public String toString() {
            return "TimePeriod{" +
                    "startHour=" + startHour +
                    ", startMinute=" + startMinute +
                    ", endHour=" + endHour +
                    ", endMinute=" + endMinute +
                    ", isFree=" + isFree +
                    '}';
        }
    }
}
