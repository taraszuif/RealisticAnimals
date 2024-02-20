package me.zuif.rean.api.config.wrapper;

import me.zuif.rean.api.animal.AgeGenderRange;

public class AgeGenderConfigRange {
    private ConfigAge age;
    private ConfigGender gender;
    private ConfigRange range;

    public AgeGenderConfigRange(ConfigAge age, ConfigGender gender, ConfigRange range) {
        this.age = age;
        this.gender = gender;
        this.range = range;
    }

    public ConfigAge getAge() {
        return age;
    }

    public void setAge(ConfigAge age) {
        this.age = age;
    }

    public AgeGenderRange getNonConfigVersion() {
        return new AgeGenderRange(age, gender, range);
    }

    public ConfigGender getGender() {
        return gender;
    }

    public void setGender(ConfigGender gender) {
        this.gender = gender;
    }

    public ConfigRange getRange() {
        return range;
    }

    public void setRange(ConfigRange range) {
        this.range = range;
    }
}
