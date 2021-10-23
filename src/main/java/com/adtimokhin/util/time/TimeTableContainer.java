package com.adtimokhin.util.time;

import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@Component
public class TimeTableContainer {

    @Autowired
    private WashingBookingMachineService washingBookingMachineService;

    @Autowired
    private DryingBookingMachineService dryingBookingMachineService;

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @Autowired
    private DryingBookingMachineBookingService dryingBookingMachineBookingService;

    @Autowired
    private Sorter sorter;

    public List<TimeTable> washingMachineTimeTables;
    public List<TimeTable> dryingMachineTimeTables;

    private String day;


    private final DateFormatResolver dateFormatResolver = new DateFormatResolver();

    public TimeTable getTimeTable(int id, boolean isWashingMachine) {
        if (isWashingMachine) {
            if (washingMachineTimeTables == null) {
                initializeLists();
            }
            try {
                return washingMachineTimeTables.get(id - 1);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }

        }
        if (dryingMachineTimeTables == null) {
            initializeLists();
        }
        try {
            return dryingMachineTimeTables.get(id - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public void updateTimeTable(int id, boolean isWashingMachine) {
        if (isWashingMachine) {
            if (washingMachineTimeTables == null) {
                initializeLists();
            }
            TimeTable timeTable = new TimeTable();
            WashingMachine washingMachine = washingBookingMachineService.findById(washingMachineTimeTables.get(id - 1).getMachine().getId());
            if (washingMachine == null) {
                return;
            }
            timeTable.generateTimeTable(washingMachine, washingBookingMachineBookingService, day, sorter);
            washingMachineTimeTables.set((id - 1), timeTable);
            return;


        }
        if (dryingMachineTimeTables == null) {
            initializeLists();
        }
        TimeTable timeTable = new TimeTable();
        DryingMachine dryingMachine = dryingBookingMachineService.findById(dryingMachineTimeTables.get(id - 1).getMachine().getId());
        if (dryingMachine == null) {
            return;
        }
        timeTable.generateTimeTable(dryingMachine, dryingBookingMachineBookingService, day, sorter);
        dryingMachineTimeTables.set((id - 1), timeTable);
    }

    public List<TimeTable> getWashingMachineTimeTables() {
        if (washingMachineTimeTables == null) {
            initializeLists();
        }
        return washingMachineTimeTables;
    }

    public List<TimeTable> getDryingMachineTimeTables() {
        if (dryingMachineTimeTables == null) {
            initializeLists();
        }
        return dryingMachineTimeTables;
    }

    private void initializeLists() {
        List<WashingMachine> washingMachineList = washingBookingMachineService.findAll();
        List<DryingMachine> dryingMachineList = dryingBookingMachineService.findAll();

        if (day == null) {
            day = dateFormatResolver.today();
        }

        washingMachineTimeTables = new ArrayList<>();
        dryingMachineTimeTables = new ArrayList<>();

        for (WashingMachine washingMachine :
                washingMachineList) {
            TimeTable timeTable = new TimeTable();
            timeTable.generateTimeTable(washingMachine, washingBookingMachineBookingService, day, sorter);
            washingMachineTimeTables.add(timeTable);
        }

        for (DryingMachine dryingMachine :
                dryingMachineList) {
            TimeTable timeTable = new TimeTable();
            timeTable.generateTimeTable(dryingMachine, dryingBookingMachineBookingService, day, sorter);
            dryingMachineTimeTables.add(timeTable);
        }
    }

    public void changeDay(String newDay) {
        day = newDay;
        initializeLists();
    }

    public String getDay() {
        if (day == null) {
            day = dateFormatResolver.today();
        }
        return day;
    }
}
