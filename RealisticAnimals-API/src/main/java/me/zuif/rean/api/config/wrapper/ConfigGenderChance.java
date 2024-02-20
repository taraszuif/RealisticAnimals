package me.zuif.rean.api.config.wrapper;

import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.animal.GenderChance;

public class ConfigGenderChance extends GenderChance {
    private String path;

    public ConfigGenderChance(double value, Gender gender, String path) {
        super(value, gender);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
