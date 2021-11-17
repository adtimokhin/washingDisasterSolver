package com.adtimokhin.util.time;

import com.adtimokhin.util.backup.BackupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

public class DateFormatResolver {

    private final static Logger logger = LoggerFactory.getLogger(DateFormatResolver.class);


    public static final String DATE_FORMAT = "YYYY MM dd HH:mm";

    public static final String DUMMY_DATE = "0000 00 00 00:00";

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;

    public static final long MINUTES_BEFORE_CANCEL = 60 * 2; // 2 hours, but in minutes.

    public static final long MAX_LENGTH_FOR_BOOKING = 60 * 2 + 20; // 140 minutes.
    public static final long MAX_LENGTH_FOR_BOOKING_WASHING_MACHINE = 60 * 2 + 20; // 140 minutes.
    public static final long MAX_LENGTH_FOR_BOOKING_DRYING_MACHINE = 60 * 2 + 20; // 140 minutes.


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

    public boolean areFarEnoughInTime(String dateOne, String dateTwo, long distance) {
        int minuteDifference = 0;

        // comparing years
        int yearDate = getDatePart(dateOne, YEAR);
        int yearToday = getDatePart(dateTwo, YEAR);
        minuteDifference += (yearDate - yearToday) * 365 * 24 * 60;
//        logger.trace("Minute difference YY : {}", minuteDifference);
//        if(minuteDifference >= MINUTES_BEFORE_CANCEL){
//            return true;
//        }

        //comparing months
        int monthDate = getDatePart(dateOne, MONTH);
        int monthToday = getDatePart(dateTwo, MONTH);
        minuteDifference += (monthDate - monthToday) * 30 * 24 * 60;
//        logger.trace("Minute difference MM : {}", minuteDifference);

//        if(minuteDifference >= distance){
//            return true;
//        }

        //comparing days
        int dayDate = getDatePart(dateOne, DAY);
        int dayToday = getDatePart(dateTwo, DAY);
        minuteDifference += (dayDate - dayToday) * 24 * 60;
//        logger.trace("Minute difference DD : {}", minuteDifference);
//        if(minuteDifference >= distance){
//            return true;
//        }

        //comparing hours
        int hourDate = getDatePart(dateOne, HOUR);
        int hourToday = getDatePart(dateTwo, HOUR);
        minuteDifference += (hourDate - hourToday) * 60;
//        logger.trace("Minute difference HH : {}", minuteDifference);

//        if(minuteDifference >= distance){
//            return true;
//        }

        //comparing minutes
        int minuteDate = getDatePart(dateOne, MINUTE);
        int minuteToday = getDatePart(dateTwo, MINUTE);
        minuteDifference += (minuteDate - minuteToday);
//        logger.trace("Minute difference MM : {}", minuteDifference);


        return minuteDifference > (distance);
    }

    public boolean appropriateFormat(String date) {
        String[] parts = date.split(" ");
        String[] origParts = DATE_FORMAT.split(" ");

        if (parts.length != origParts.length) {
            return false;
        }

        for (int i = 0; i < origParts.length - 1; i++) {
            try {
                Integer.parseInt(parts[i]);
                if (parts[i].length() != origParts[i].length()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        String[] hourMinParts = parts[parts.length - 1].split(":");
        String[] origHourMinParts = origParts[origParts.length - 1].split(":");

        if (hourMinParts.length != origHourMinParts.length) {
            return false;
        }

        for (int i = 0; i < origHourMinParts.length; i++) {
            try {
                Integer.parseInt(hourMinParts[i]);
                if (hourMinParts[i].length() != origHourMinParts[i].length()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    public boolean isInAppropriateFormat(String date){
        if(date.length() != "2021-01-01".length()){
            return false;
        }

        String[] parts = date.split("-");

        if(parts.length != 3){
            return false;
        }

        for (int i = 0; i < parts.length; i++) {
            try{
                int integer = Integer.parseInt(parts[i]);
                if(i == 0){
                    if(integer < 2020 || integer > 3000) {
                        return false;
                    }
                }else {
                    if(integer < 0 || integer > 32){
                        return false;
                    }
                }
            }catch (Exception e){
                return false;
            }
        }

        return true;
    }
}
