package me.zuif.rean.api.handler;

import me.zuif.rean.api.animal.Age;
import me.zuif.rean.api.animal.Gender;
import me.zuif.rean.api.config.file.AnimalsConfig;

import java.util.HashMap;
import java.util.Map;

public class RangeHandler {
    private final Map<AnimalsConfig.AgeGenderRangeType, Double> ageGenderMap;
    private final Map<AnimalsConfig.RangeType, Double> rangeMap;
    private ConfigHandler configHandler;
    private Age age;
    private Gender gender;

    RangeHandler(ConfigHandler configHandler, Age age, Gender gender) {
        this.ageGenderMap = new HashMap<>();
        this.rangeMap = new HashMap<>();
        this.configHandler = configHandler;
        this.age = age;
        this.gender = gender;
        load();
    }

    public RangeHandler(Map<AnimalsConfig.AgeGenderRangeType, Double> ageGenderMap, Map<AnimalsConfig.RangeType, Double> rangeMap) {
        this.ageGenderMap = ageGenderMap;
        this.rangeMap = rangeMap;
    }

    public void load() {
        for (AnimalsConfig.AgeGenderRangeType type : AnimalsConfig.AgeGenderRangeType.values()) {
            putAgeGender(type, configHandler.getConfig().getAgeGenderRange(age, gender, type).getRandomValue());
        }

        for (AnimalsConfig.RangeType type : AnimalsConfig.RangeType.values()) {
            putRange(type, configHandler.getConfig().getRange(type).getRandomValue());
        }
    }

    public void putRange(AnimalsConfig.RangeType type, double value) {
        rangeMap.put(type, value);
    }

    public double getRange(AnimalsConfig.RangeType type) {
        return rangeMap.get(type);
    }

    public void putAgeGender(AnimalsConfig.AgeGenderRangeType type, double value) {
        ageGenderMap.put(type, value);
    }

    public double getAgeGender(AnimalsConfig.AgeGenderRangeType type) {
        return ageGenderMap.get(type);
    }


}
