package com.adtimokhin.util.time;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.Machine;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.util.Sorter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.adtimokhin.util.time.DateFormatResolver.HOUR;
import static com.adtimokhin.util.time.DateFormatResolver.MINUTE;


/**
 * @author adtimokhin
 * 16.10.2021
 **/


public class TimeTable {

    @Getter
    private Machine machine;

    private final static DateFormatResolver dateFormatResolver = new DateFormatResolver();

    @Getter
    private List<TimePeriod> timePeriods;

    public void generateTimeTable(WashingMachine machine, WashingBookingMachineBookingService washingMachineBookingService, String date, Sorter sorter) {

        this.machine = machine;

        List<WashingMachineBooking> washingMachineBookings = sorter.sortWashingMachineBooking(sorter.clearData(washingMachineBookingService.getBookings(machine), date));

        int washingMachineBookingsSize = washingMachineBookings.size();

        timePeriods = new ArrayList<>();

        timePeriods.add(new TimePeriod(0, 0, 6, 0, false));
        for (int i = 0; i < washingMachineBookingsSize; i++) {
            WashingMachineBooking current = washingMachineBookings.get(i);
            // create TimePeriod for current Booking
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();

            String oldEnd = timePeriods.get(i).getDate();

            if (!dateFormatResolver.datesMatch(startDate, oldEnd)) {
                // create new a free slot in between
                timePeriods.add(new TimePeriod(
                        dateFormatResolver.getDatePart(oldEnd, HOUR),
                        dateFormatResolver.getDatePart(oldEnd, MINUTE),
                        dateFormatResolver.getDatePart(startDate, HOUR),
                        dateFormatResolver.getDatePart(startDate, MINUTE),
                        true
                ));
            }

            timePeriods.add(new TimePeriod(dateFormatResolver.getDatePart(startDate, HOUR),
                    dateFormatResolver.getDatePart(startDate, MINUTE),
                    dateFormatResolver.getDatePart(endDate, HOUR),
                    dateFormatResolver.getDatePart(endDate, MINUTE),
                    false));
        }

        String oldEnd = timePeriods.get(timePeriods.size()-1).getDate();
        String startDate = "0000 00 00 22:00";

        if (!dateFormatResolver.datesMatch(startDate, oldEnd)) {
            // create new a free slot in between
            timePeriods.add(new TimePeriod(
                    dateFormatResolver.getDatePart(oldEnd, HOUR),
                    dateFormatResolver.getDatePart(oldEnd, MINUTE),
                    dateFormatResolver.getDatePart(startDate, HOUR),
                    dateFormatResolver.getDatePart(startDate, MINUTE),
                    true
            ));
        }

        timePeriods.add(new TimePeriod(22, 0, 23, 59, false));
    }

    public void generateTimeTable(DryingMachine machine, DryingBookingMachineBookingService dryingMachineBookingService,  String date, Sorter sorter) {

        this.machine = machine;

        List<DryingMachineBooking> dryingMachineBookings = sorter.sortDryingMachineBookings(sorter.clearDataDrying(dryingMachineBookingService.getBookings(machine), date));

        int dryingMachineBookingsSize = dryingMachineBookings.size();

        timePeriods = new ArrayList<>();

        timePeriods.add(new TimePeriod(0, 0, 6, 0, false));
        for (int i = 0; i < dryingMachineBookingsSize; i++) {
            DryingMachineBooking current = dryingMachineBookings.get(i);
            // create TimePeriod for current Booking
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();

            String oldEnd = timePeriods.get(i).getDate();

            if (!dateFormatResolver.datesMatch(startDate, oldEnd)) {
                // create new a free slot in between
                timePeriods.add(new TimePeriod(
                        dateFormatResolver.getDatePart(oldEnd, HOUR),
                        dateFormatResolver.getDatePart(oldEnd, MINUTE),
                        dateFormatResolver.getDatePart(startDate, HOUR),
                        dateFormatResolver.getDatePart(startDate, MINUTE),
                        true
                ));
            }

            timePeriods.add(new TimePeriod(dateFormatResolver.getDatePart(startDate, HOUR),
                    dateFormatResolver.getDatePart(startDate, MINUTE),
                    dateFormatResolver.getDatePart(endDate, HOUR),
                    dateFormatResolver.getDatePart(endDate, MINUTE),
                    false));
        }

        String oldEnd = timePeriods.get(timePeriods.size()-1).getDate();
        String startDate = "0000 00 00 22:00";

        if (!dateFormatResolver.datesMatch(startDate, oldEnd)) {
            // create new a free slot in between
            timePeriods.add(new TimePeriod(
                    dateFormatResolver.getDatePart(oldEnd, HOUR),
                    dateFormatResolver.getDatePart(oldEnd, MINUTE),
                    dateFormatResolver.getDatePart(startDate, HOUR),
                    dateFormatResolver.getDatePart(startDate, MINUTE),
                    true
            ));
        }

        timePeriods.add(new TimePeriod(22, 0, 23, 59, false));
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

        public String getDate() {
            String h = String.valueOf(endHour);
            if (endHour < 10) {
                h = "0" + h;
            }
            String m = String.valueOf(endMinute);
            if (endMinute < 10) {
                m = "0" + m;
            }

            return "0000 00 00 " + h + ":" + m;
        }


    }
}
