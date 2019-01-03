package com.mphj.todo.adapters.models;

import android.content.Context;

public class TimeSuggestObj implements Comparable<TimeSuggestObj> {

    public String date;
    public int dateRes = -1;
    public long offsetInMilies;
    public boolean isSelected;

    public TimeSuggestObj(String date, long offsetInMinutes) {
        this.date = date;
        this.offsetInMilies = offsetInMinutes;
    }

    public TimeSuggestObj(int dateRes, long offsetInMinutes) {
        this.dateRes = dateRes;
        this.offsetInMilies = offsetInMinutes;
    }


    public String getDate(Context context) {
        if (dateRes == -1) {
            return date;
        }
        return context.getString(dateRes);
    }

    @Override
    public int compareTo(TimeSuggestObj o) {
        long currDiff = offsetInMilies;
        long nextDiff = o.offsetInMilies;
        if (currDiff > nextDiff)
            return 1;
        else if (currDiff < nextDiff)
            return -1;
        else
            return 0;
    }
}
