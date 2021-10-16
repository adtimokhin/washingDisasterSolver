package com.adtimokhin.util;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.Machine;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.adtimokhin.util.DateFormatResolver.HOUR;
import static com.adtimokhin.util.DateFormatResolver.MINUTE;


/**
 * @author adtimokhin
 * 16.10.2021
 **/


public class TimeTable {

    @Getter
    private Machine machine;

    private final static DateFormatResolver dateFormatResolver = new DateFormatResolver();
    private final static Sorter sorter = new Sorter();

    @Getter
    private List<TimePeriod> timePeriods;

    public void generateTimeTable(WashingMachine machine, WashingBookingMachineBookingService washingMachineBookingService) {

        this.machine = machine;

        List<WashingMachineBooking> washingMachineBookings = sorter.sortWashingMachineBooking(washingMachineBookingService.getBookings(machine));

        int washingMachineBookingsSize = washingMachineBookings.size();

        timePeriods = new ArrayList<>();
        for (int i = 0; i < washingMachineBookingsSize; i++) {
            WashingMachineBooking current = washingMachineBookings.get(i);
            // create TimePeriod for current Booking
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();
            timePeriods.add(new TimePeriod(dateFormatResolver.getDatePart(startDate, HOUR),
                    dateFormatResolver.getDatePart(startDate, MINUTE),
                    dateFormatResolver.getDatePart(endDate, HOUR),
                    dateFormatResolver.getDatePart(endDate, MINUTE),
                    false));

            if (i == washingMachineBookingsSize - 1) {
                continue;
            }

            WashingMachineBooking next = washingMachineBookings.get(i + 1);
            // comparing two booking dates
            String nextStartDate = next.getStartDate();
            if (!dateFormatResolver.datesMatch(nextStartDate, endDate)) {
                // create new a free slot in between
                timePeriods.add(new TimePeriod(
                        dateFormatResolver.getDatePart(endDate, HOUR),
                        dateFormatResolver.getDatePart(endDate, MINUTE),
                        dateFormatResolver.getDatePart(nextStartDate, HOUR),
                        dateFormatResolver.getDatePart(nextStartDate, MINUTE),
                        true
                ));
            }
        }
    }

    public void generateTimeTable(DryingMachine machine, DryingBookingMachineBookingService dryingMachineBookingService) {
        this.machine = machine;

        List<DryingMachineBooking> dryingMachineBookings = dryingMachineBookingService.getBookings(machine);

        int washingMachineBookingsSize = dryingMachineBookings.size();

        timePeriods = new ArrayList<>();
        for (int i = 0; i < washingMachineBookingsSize; i++) {
            DryingMachineBooking current = dryingMachineBookings.get(i);
            // create TimePeriod for current Booking
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();
            timePeriods.add(new TimePeriod(dateFormatResolver.getDatePart(startDate, HOUR),
                    dateFormatResolver.getDatePart(startDate, MINUTE),
                    dateFormatResolver.getDatePart(endDate, HOUR),
                    dateFormatResolver.getDatePart(endDate, MINUTE),
                    false));

            if (i == washingMachineBookingsSize - 1) {
                continue;
            }

            DryingMachineBooking next = dryingMachineBookings.get(i + 1);
            // comparing two booking dates
            String nextStartDate = next.getStartDate();
            if (!dateFormatResolver.datesMatch(nextStartDate, endDate)) {
                // create new a free slot in between
                timePeriods.add(new TimePeriod(
                        dateFormatResolver.getDatePart(endDate, HOUR),
                        dateFormatResolver.getDatePart(endDate, MINUTE),
                        dateFormatResolver.getDatePart(nextStartDate, HOUR),
                        dateFormatResolver.getDatePart(nextStartDate, MINUTE),
                        true
                ));
            }
        }
    }


    public boolean isFreeBetween(int startHour, int startMinute, int endHour, int endMinute) {
        for (TimePeriod timePeriod : timePeriods) {
            if (timePeriod.isFree) {
                if (dateFormatResolver.isTimeBigger(startHour, startMinute, timePeriod.startHour, timePeriod.startMinute)) {
                    if (dateFormatResolver.isTimeBigger(timePeriod.endHour, timePeriod.endMinute, endHour, endMinute)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "timePeriods=" + timePeriods +
                '}';
    }

    public static class TimePeriod {
        int startHour;
        int startMinute;
        int endHour;
        int endMinute;
        @Getter
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

        public String getTimeBounds() {
            return "Start: " + startHour + ":" + startMinute + " End: " + endHour + ":" + endMinute;
        }


    }
}
