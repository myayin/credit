package com.colendi.credit.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class DateUtil {

    public static Instant resetTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().toInstant();
    }
}
