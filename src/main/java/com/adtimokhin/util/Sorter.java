package com.adtimokhin.util;

import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
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
}
