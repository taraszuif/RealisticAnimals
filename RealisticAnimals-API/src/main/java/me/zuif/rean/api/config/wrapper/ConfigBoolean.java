package me.zuif.rean.api.config.wrapper;

public class ConfigBoolean {
    private boolean value;
    private String path;

    public ConfigBoolean(boolean value, String path) {
        this.value = value;
        this.path = path;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
