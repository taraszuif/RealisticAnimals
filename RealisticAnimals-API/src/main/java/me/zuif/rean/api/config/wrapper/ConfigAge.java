package me.zuif.rean.api.config.wrapper;

import me.zuif.rean.api.animal.Age;

public class ConfigAge extends Age {
    private String path;

    public ConfigAge(double age, String description, String path) {
        super(age, description);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
