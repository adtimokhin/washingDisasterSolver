package com.adtimokhin.service.bookings.impl;

import com.adtimokhin.aspect.NotEmptyArguments;
import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.repository.bookings.WashingMachineBookingRepository;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.Sorter;
import com.adtimokhin.util.time.DateFormatResolver;
import com.adtimokhin.util.time.TimeTableContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Component
public class WashingBookingMachineBookingServiceImpl implements WashingBookingMachineBookingService {

    @Autowired
    private TimeTableContainer timeTableContainer;

    @Autowired
    private WashingMachineBookingRepository washingMachineBookingRepository;

    @Autowired
    private WashingBookingMachineService washingBookingMachineService;

    private final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    @Override
    public List<WashingMachineBooking> getBookings(WashingMachine machine) {
        return washingMachineBookingRepository.findWashingMachineBookingByWashingMachineIdOrderByIdAsc(machine.getId());
    }

    @Override
    public void save(WashingMachineBooking booking) {
        washingMachineBookingRepository.save(booking);

        timeTableContainer.updateTimeTable(booking.getWashingMachine().getId(), true);
    }

    @Override
    public void delete(WashingMachineBooking booking) {
        int id = booking.getWashingMachine().getId();
        washingMachineBookingRepository.delete(booking);

        timeTableContainer.updateTimeTable(id, true);
    }

    @Override
    public WashingMachineBooking findById(int id) {
        return washingMachineBookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<WashingMachineBooking> findAllByUser(User user) {
        return washingMachineBookingRepository.findAllByUserIdOrderByIdAsc(user.getId());
    }

    @Override
    public List<WashingMachineBooking> findAllByWashingMachineId(int id) {
        return washingMachineBookingRepository.findAllByWashingMachineId(id);
    }

    @Override
    public WashingMachineBooking getBookingForMachineWithId(int id, String date) {
        Sorter sorter = new Sorter();
        List<WashingMachineBooking> bookings = findAllByWashingMachineId(id);
        if (bookings == null) {
            return null;
        }
        bookings = sorter.sortWashingMachineBooking(sorter.clearData(bookings, date));
        WashingMachineBooking previousWashingMachineBooking = null;
        WashingMachineBooking currentMachineBooking = null;
        for (WashingMachineBooking booking :
                bookings) {
            if (dateFormatResolver.isTimeBigger(date, booking.getStartDate())) {
                if (dateFormatResolver.isTimeBigger(booking.getEndDate(), date)) {
                    currentMachineBooking = booking;
                    break;
                }
            }

            previousWashingMachineBooking = booking;
        }

        if (currentMachineBooking == null) {
            return null;
        }
        if (previousWashingMachineBooking == null) {
            // todo: this is the place for the improvement.
            return null;
        }
        return previousWashingMachineBooking;
    }


}
