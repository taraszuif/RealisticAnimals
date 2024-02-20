package me.zuif.rean.api.animal;


import me.zuif.rean.api.util.Chance;

import java.util.concurrent.ThreadLocalRandom;

public class GenderChance extends Chance {
    private Gender gender;

    public GenderChance(double value, Gender gender) {
        super(value);
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean check() {
        return ThreadLocalRandom.current().nextDouble() > getValue();
    }
}
