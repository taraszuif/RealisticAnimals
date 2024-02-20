package me.zuif.rean.api.util;

import java.util.concurrent.ThreadLocalRandom;

public class Range {
    private double start;
    private double end;

    public Range(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public static double getRandomValue(double start, double end) {
        return ThreadLocalRandom.current().nextDouble(start, end);
    }

    public double getRandomValue() {
        return ThreadLocalRandom.current().nextDouble(start, end);
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
