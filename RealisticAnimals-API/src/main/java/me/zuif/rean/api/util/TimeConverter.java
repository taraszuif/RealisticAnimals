package me.zuif.rean.api.util;

import me.zuif.rean.api.ReAnAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {
    private static double secondPerTick;
    private final long value;

    public TimeConverter(long value) {
        this.value = value;
        TimeConverter.secondPerTick = ReAnAPI.getInstance().getConfigManager().getConfig().getTimeRatio();
    }

    public static long convertYearsToTicks(double years) {
        return (long) years * 365 * 24 * 60 * 60 * 20;
    }

    public double convertToSeconds() {
        return value * secondPerTick;
    }

    public double convertToMinutes() {
        return convertToSeconds() / 60;
    }

    public double convertToHours() {
        return convertToMinutes() / 60;
    }

    public double convertToDays() {
        return convertToHours() / 24;
    }

    public double convertToMonths() {
        return convertToDays() / 30;
    }

    public double convertToYears() {
        return convertToMonths() / 12;
    }

    public Date getDate() {
        double seconds = convertToSeconds();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date((long) (seconds * 1000));
        return date;
    }

    public String formatTimeFromDate() {
        double seconds = convertToSeconds();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date((long) (seconds * 1000));
        String formattedTime = dateFormat.format(date);
        return formattedTime;
    }

}
