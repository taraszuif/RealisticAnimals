package me.zuif.rean.api.config.wrapper;


import me.zuif.rean.api.util.Range;

public class ConfigRange extends Range {
    private String path;

    public ConfigRange(double start, double end, String path) {
        super(start, end);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
