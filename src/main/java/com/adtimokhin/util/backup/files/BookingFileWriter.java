package com.adtimokhin.util.backup.files;

import com.adtimokhin.model.bookings.Booking;
import com.adtimokhin.model.bookings.WashingMachineBooking;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

public class BookingFileWriter implements FileWorker {
    private BufferedWriter writer;

    @Override
    public void openConnection(String filename) throws IOException {
        writer = new BufferedWriter(new java.io.FileWriter(filename));
    }

    public void saveWashingMachineBooking(Booking booking) throws IOException {
        writer.write(booking.toString());
        writer.write("\n");
        writer.flush();
    }

    @Override
    public void closeConnection() throws IOException {
        writer.close();
    }
}
