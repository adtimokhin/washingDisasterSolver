package com.adtimokhin.util.backup;

import com.adtimokhin.model.bookings.DryingMachineBooking;
import com.adtimokhin.model.bookings.WashingMachineBooking;
import com.adtimokhin.model.machine.DryingMachine;
import com.adtimokhin.model.machine.WashingMachine;
import com.adtimokhin.service.bookings.DryingBookingMachineBookingService;
import com.adtimokhin.service.bookings.WashingBookingMachineBookingService;
import com.adtimokhin.service.machine.DryingBookingMachineService;
import com.adtimokhin.service.machine.WashingBookingMachineService;
import com.adtimokhin.util.time.DateFormatResolver;
import com.adtimokhin.util.Sorter;
import com.adtimokhin.util.backup.files.BookingFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author adtimokhin
 * 23.10.2021
 **/
@Component
public class BackupUtil {

    @Autowired
    private WashingBookingMachineBookingService washingBookingMachineBookingService;

    @Autowired
    private WashingBookingMachineService washingBookingMachineService;

    @Autowired
    private DryingBookingMachineBookingService dryingBookingMachineBookingService;

    @Autowired
    private DryingBookingMachineService dryingBookingMachineService;

    private DateFormatResolver dateFormatResolver = new DateFormatResolver();

    private static final String ABSOLUTE_PATH = "/Users/atimokhina/Desktop/washingDisasterSolver/";


    @Scheduled(cron = "0 32 17 * * ?", zone = "Europe/London")
    public void saveBookingInformation() throws IOException {
        Sorter sorter = new Sorter();
        BookingFileWriter bookingFileWriter = new BookingFileWriter();
        String date = dateFormatResolver.today();
        String today = date.replace(" ", "_");
        String fileName = "bookings_" + today.substring(0, (today.length() - 5)) + ".txt";

        File saveFile = new File(ABSOLUTE_PATH + fileName);
        saveFile.createNewFile();

        bookingFileWriter.openConnection(saveFile.getAbsolutePath());
        // save all data
        List<WashingMachine> washingMachines = washingBookingMachineService.findAll();
        for (WashingMachine machine :
                washingMachines) {
            List<WashingMachineBooking> washingMachineBookings = sorter.clearData(washingBookingMachineBookingService.getBookings(machine), date);
            for (WashingMachineBooking booking :
                    washingMachineBookings) {
                bookingFileWriter.saveWashingMachineBooking(booking);
                delete(booking);
            }
        }
        List<DryingMachine> dryingMachines = dryingBookingMachineService.findAll();
        for (DryingMachine machine :
                dryingMachines) {
            List<DryingMachineBooking> dryingMachineBookings = sorter.clearDataDrying(dryingBookingMachineBookingService.getBookings(machine), date);
            for (DryingMachineBooking booking :
                    dryingMachineBookings) {
                bookingFileWriter.saveWashingMachineBooking(booking);
                delete(booking);
            }
        }

        bookingFileWriter.closeConnection();
    }

    @Scheduled(cron = "0 30 23 * * ?", zone = "Europe/London")
    public void getRidOffIrrelevantBookingData() {
        // todo: this is a potential extension of functionality...
    }

    private void delete(DryingMachineBooking booking) {
        dryingBookingMachineBookingService.delete(booking);
    }

    private void delete(WashingMachineBooking booking) {
        washingBookingMachineBookingService.delete(booking);
    }
}
