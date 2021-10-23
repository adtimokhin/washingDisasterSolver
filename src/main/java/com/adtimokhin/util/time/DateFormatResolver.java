package com.adtimokhin.util.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        return startHour == endHour && startMinute >= endMinute;
    }

    public boolean isTimeBigger(String timeOne, String timeTwo) {
        return isTimeBigger(getDatePart(timeOne, HOUR),
                getDatePart(timeOne, MINUTE),
                getDatePart(timeTwo, HOUR),
                getDatePart(timeTwo, MINUTE));
    }

    public String resolveTimeForDate(String hour, String minute, String date) {
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }

        return extractYearMonthDay(date) + hour + ":" + minute;
    }

    public String extractYearMonthDay(String date) {
        return date.substring(0, 11);
    }

    public boolean isDateBeforeAnother(String dateOne, String dateTwo) {
        if (getDatePart(dateOne, YEAR) == getDatePart(dateTwo, YEAR)) {
            if (getDatePart(dateOne, MONTH) == getDatePart(dateTwo, MONTH)) {
                if (getDatePart(dateOne, DAY) == getDatePart(dateTwo, DAY)) {
                    if (getDatePart(dateOne, HOUR) == getDatePart(dateTwo, HOUR)) {
                        return getDatePart(dateOne, MINUTE) < getDatePart(dateTwo, MINUTE);
                    } else {
                        return getDatePart(dateOne, HOUR) < getDatePart(dateTwo, HOUR);
                    }
                } else {
                    return getDatePart(dateOne, DAY) < getDatePart(dateTwo, DAY);
                }
            } else {
                return getDatePart(dateOne, MONTH) < getDatePart(dateTwo, MONTH);
            }
        } else {
            return getDatePart(dateOne, YEAR) < getDatePart(dateTwo, YEAR);
        }
    }

    public String today() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public boolean onTheSameDay(String timeOne, String timeTwo) {
        // for this class we assume that we use DATE_FORMAT
        int dayOne = getDatePart(timeOne, DAY);
        int dayTwo = getDatePart(timeTwo, DAY);

        if (dayOne == dayTwo) {

            int monthOne = getDatePart(timeOne, MONTH);
            int monthTwo = getDatePart(timeTwo, MONTH);

            if (monthOne == monthTwo) {

                int yearOne = getDatePart(timeOne, YEAR);
                int yearTwo = getDatePart(timeTwo, YEAR);

                return yearOne == yearTwo;
            }
        }
        return false;
    }

    public boolean timeComesAfterAnother(int hourOne, int minuteTwo, String timeTwo) {
        return false;
    }

}
