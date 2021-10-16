package com.adtimokhin.util;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

public class DateFormatResolver {

    public static final String DATE_FORMAT = "YYYY MM dd HH:mm";

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;


    public int getDatePart(String date, int part) {
        String[] parts = date.split(" ");
        if (part < 3) {
            try {
                return Integer.parseInt(parts[part]);
            } catch (Exception e) {
                return -1;
            }
        } else {
            String[] hoursMinutes = parts[3].split(":");
            try {
                return Integer.parseInt(hoursMinutes[part - 3]);
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public boolean datesMatch(String start, String end) {

        if (getDatePart(start, MINUTE) == getDatePart(end, MINUTE)) {
            return getDatePart(start, HOUR) == getDatePart(end, HOUR);
        }
        return false;
    }

    public boolean isTimeBigger(int startHour, int startMinute, int endHour, int endMinute) {
        if (startHour > endHour) {
            return true;
        }
        return startHour == endHour && startMinute > startHour;
    }
}
