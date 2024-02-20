package me.zuif.rean.api.handler;

import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.animal.GenderChance;
import me.zuif.rean.api.config.file.AnimalsConfig;

public class GenderHandler {
    private ConfigHandler configHandler;
    private Gender gender;


    GenderHandler(ConfigHandler handler) {
        configHandler = handler;
        load();
    }

    public GenderHandler(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void load() {
        AnimalsConfig config = configHandler.getConfig();
        Gender result = null;
        while (result == null) {
            for (GenderChance chance : config.getGenderChances()) {
                if (chance.check()) {
                    result = chance.getGender();
                }
            }
        }
        this.gender = result;
    }
}
