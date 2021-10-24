package com.adtimokhin.service.bookings.impl;

import com.adtimokhin.model.User;
import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.repository.bookings.DryingMachineBookingRepository;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
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
public class DryingBookingMachineBookingServiceImpl implements DryingBookingMachineBookingService {

    @Autowired
    private TimeTableContainer timeTableContainer;

    @Autowired
    private DryingMachineBookingRepository dryingMachineBookingRepository;

    @Autowired
    private DryingBookingMachineService dryingBookingMachineService;

    private final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    @Override
    public List<DryingMachineBooking> getBookings(DryingMachine machine) {
        return dryingMachineBookingRepository.findDryingMachineBookingsByDryingMachineIdOrderByIdAsc(machine.getId());
    }

    @Override
    public void save(DryingMachineBooking booking) {
        dryingMachineBookingRepository.save(booking);

        timeTableContainer.updateTimeTable(booking.getDryingMachine().getId(), false);
    }

    @Override
    public void delete(DryingMachineBooking booking) {
        dryingMachineBookingRepository.delete(booking);
    }

    @Override
    public List<DryingMachineBooking> findAllByUser(User user) {
        return dryingMachineBookingRepository.findAllByUserIdOrderByIdAsc(user.getId());
    }

    @Override
    public DryingMachineBooking findById(int id) {
        return dryingMachineBookingRepository.findById(id).orElse(null);
    }

    @Override
    public DryingMachineBooking getBookingForMachineWithId(int id, String date) {
        Sorter sorter = new Sorter();
        List<DryingMachineBooking> bookings = sorter.sortDryingMachineBookings(sorter.clearDataDrying(dryingBookingMachineService.findById(id).getDryingMachineBookingList(), date));
        DryingMachineBooking previousWashingMachineBooking = null;
        DryingMachineBooking currentMachineBooking = null;
        for (DryingMachineBooking booking :
                bookings) {
            if (dateFormatResolver.isTimeBigger(date , booking.getStartDate())){
                if(dateFormatResolver.isTimeBigger(booking.getEndDate(), date)){
                    currentMachineBooking = booking;
                    break;
                }
            }

            previousWashingMachineBooking = booking;
        }

        if(currentMachineBooking == null){
            return null;
        }
        if(previousWashingMachineBooking == null){
            // todo: this is the place for the improvement.
            return null;
        }
        return previousWashingMachineBooking;
    }

}
