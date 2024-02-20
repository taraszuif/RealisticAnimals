package me.zuif.rean.api.config.wrapper;

public class ConfigDouble {
    private double value;
    private String path;

    public ConfigDouble(double value, String path) {
        this.value = value;
        this.path = path;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
