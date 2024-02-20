package me.zuif.rean.api.config.wrapper;

import me.zuif.rean.api.animal.Gender;

public class ConfigGender extends Gender {
    private String path;

    public ConfigGender(String name, String path) {
        super(name);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
