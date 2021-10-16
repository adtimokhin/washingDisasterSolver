package com.adtimokhin.util;

import com.adtimokhin.model.bookings.WashingMachineBooking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

public class Sorter {


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
}
