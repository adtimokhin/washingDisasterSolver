package com.adtimokhin.util;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.util.time.DateFormatResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Component
public class Sorter {

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;


    private static final DateFormatResolver dateTimeResolver = new DateFormatResolver();

    public List<WashingMachineBooking> sortWashingMachineBooking(List<WashingMachineBooking> unsortedList) {
        for (int i = 0; i < unsortedList.size(); i++) {
            for (int j = 0; j < unsortedList.size() - i; j++) {
                if (j == unsortedList.size() - i - 1) {
                    continue;
                }
                WashingMachineBooking highest = unsortedList.get(j);
                WashingMachineBooking lower = unsortedList.get(j + 1);

                if (dateTimeResolver.isTimeBigger(highest.getEndDate(), lower.getEndDate())) {
                    unsortedList.set((j + 1), highest);
                    unsortedList.set(j, lower);
                }

            }
        }

        return unsortedList;

    }

    public List<WashingMachineBooking> clearData(List<WashingMachineBooking> unclearList, String date) {

        List<WashingMachineBooking> washingMachineBookings = new ArrayList<>();

        for (WashingMachineBooking washingMachineBooking : unclearList) {
            if (dateTimeResolver.onTheSameDay(date, washingMachineBooking.getStartDate())) {
                washingMachineBookings.add(washingMachineBooking);
            }
        }

        return washingMachineBookings;
    }

    //
    public List<DryingMachineBooking> sortDryingMachineBookings(List<DryingMachineBooking> unsortedList) {
        for (int i = 0; i < unsortedList.size(); i++) {
            for (int j = 0; j < unsortedList.size() - i; j++) {
                if (j == unsortedList.size() - i - 1) {
                    continue;
                }
                DryingMachineBooking highest = unsortedList.get(j);
                DryingMachineBooking lower = unsortedList.get(j + 1);

                if (dateTimeResolver.isTimeBigger(highest.getEndDate(), lower.getEndDate())) {
                    unsortedList.set((j + 1), highest);
                    unsortedList.set(j, lower);
                }

            }
        }

        return unsortedList;

    }

    public List<DryingMachineBooking> clearDataDrying(List<DryingMachineBooking> unclearList, String date) {

        List<DryingMachineBooking> dryingMachineBookings = new ArrayList<>();

        for (DryingMachineBooking dryingMachineBooking : unclearList) {
            if (dateTimeResolver.onTheSameDay(date, dryingMachineBooking.getStartDate())) {
                dryingMachineBookings.add(dryingMachineBooking);
            }
        }

        return dryingMachineBookings;
    }

    public List<DryingMachineBooking> getRelevantDryingBookings(List<DryingMachineBooking> unclearList) {

        List<DryingMachineBooking> dryingMachineBookings = new ArrayList<>();
        String today = dateTimeResolver.today();

        for (DryingMachineBooking dryingMachineBooking : unclearList) {
            if (!dateTimeResolver.isDateBeforeAnother(dryingMachineBooking.getEndDate(), today)) {
                dryingMachineBookings.add(dryingMachineBooking);
            }
        }

        return dryingMachineBookings;
    }

    public List<WashingMachineBooking> getRelevantWashingBookings(List<WashingMachineBooking> unclearList) {

        List<WashingMachineBooking> washingMachineBookings = new ArrayList<>();
        String today = dateTimeResolver.today();

        for (WashingMachineBooking washingMachineBooking : unclearList) {
            if (!dateTimeResolver.isDateBeforeAnother(washingMachineBooking.getEndDate(), today)) {
                washingMachineBookings.add(washingMachineBooking);
            }
        }

        return washingMachineBookings;
    }
}
