package me.zuif.rean.api.animal;


import me.zuif.rean.api.util.Range;

public class Trust {

    private double value;
    private TrustLevel level;
    private Range boundaries;

    //TODO: implement trust system in version beta 0.7
    public Trust(double value, Range boundaries) {
        this.value = value;
        this.boundaries = boundaries;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private void checkLevel() {
        double end = boundaries.getEnd();
        double start = boundaries.getStart();


    }

    public Range getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(Range boundaries) {
        this.boundaries = boundaries;
    }

    public TrustLevel getLevel() {
        return level;
    }

    public void setLevel(TrustLevel level) {
        this.level = level;
    }
}
