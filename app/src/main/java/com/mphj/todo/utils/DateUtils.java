package com.mphj.todo.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateUtils {

    public static long nextDay(int day) {
        DateTime today = def();
        int old = today.getDayOfWeek();
        int monday = day;

        if (monday <= old) {
            monday += 7;
        }
        DateTime next = today.plusDays(monday - old);
        return next.toDate().getTime() - today.toDate().getTime();
    }

    public static DateTime def() {
        return DateTime.now().withZone(DateTimeZone.forID("Asia/Tehran"));
    }


    public static long nextMin(int min) {
        DateTime now = def();
        DateTime next = now.plusMinutes(min);
        return next.toDate().getTime() - now.toDate().getTime();
    }

    public static long todayHour(int hour) {
        DateTime now = def();
        DateTime next = now.withHourOfDay(hour);
        return next.toDate().getTime() - now.toDate().getTime();
    }

    public static long nextDay() {
        DateTime now = def();
        DateTime next = now.plusDays(1);
        return next.toDate().getTime() - now.toDate().getTime();
    }
}
