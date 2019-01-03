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

    public static boolean inSameDay(long from, long to) {
        DateTime fDate = new DateTime(from, DateTimeZone.forID("Asia/Tehran"));
        DateTime tDate = new DateTime(to, DateTimeZone.forID("Asia/Tehran"));
        return fDate.withTimeAtStartOfDay().isEqual(tDate.withTimeAtStartOfDay());
    }

    public static boolean isToday(long time) {
        return inSameDay(DateUtils.def().toDate().getTime(), time);
    }

    public static boolean isTomorrow(long time) {
        return inSameDay(DateUtils.def().plusDays(1).toDate().getTime(), time);
    }

    public static boolean isAfterTomorrow(long time) {
        return def().plusDays(2).withTimeAtStartOfDay().isBefore(new DateTime(time, DateTimeZone.forID("Asia/Tehran")));
    }

    public static DateTime def() {
        return DateTime.now().withZone(DateTimeZone.forID("Asia/Tehran"));
    }

    public static boolean isBeforeNow(long time) {
        return new DateTime(time, DateTimeZone.forID("Asia/Tehran")).isBeforeNow();
    }

    public static boolean isBeforeToday(long time) {
        return new DateTime(time, DateTimeZone.forID("Asia/Tehran")).isBefore(def().withTimeAtStartOfDay());
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
