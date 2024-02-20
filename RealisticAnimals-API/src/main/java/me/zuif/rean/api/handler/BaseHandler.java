package me.zuif.rean.api.handler;

import me.zuif.rean.api.animal.Animal;

public class BaseHandler {
    private final AgeHandler ageHandler;
    private final GenderHandler genderHandler;
    private final GoalsHandler goalsHandler;
    private final Animal type;
    private final ConfigHandler configHandler;
    private final RangeHandler rangeHandler;
    private String realisticName;

    BaseHandler(Animal type, String realisticName) {
        this.configHandler = new ConfigHandler(type);
        this.ageHandler = new AgeHandler(configHandler);
        this.genderHandler = new GenderHandler(configHandler);
        this.goalsHandler = new GoalsHandler();
        this.type = type;
        this.realisticName = realisticName;

        this.rangeHandler = new RangeHandler(configHandler, ageHandler.getAge(), genderHandler.getGender());

    }

    BaseHandler(Animal type, String realisticName, AgeHandler ageHandler, GenderHandler genderHandler, GoalsHandler goalsHandler, RangeHandler rangeHandler) {
        this.configHandler = new ConfigHandler(type);
        this.ageHandler = ageHandler;
        this.genderHandler = genderHandler;
        this.goalsHandler = goalsHandler;
        this.rangeHandler = rangeHandler;
        this.type = type;
        this.realisticName = realisticName;
    }

    public GoalsHandler getGoalsHandler() {
        return goalsHandler;
    }

    public RangeHandler getRangeHandler() {
        return rangeHandler;
    }

    public Animal getType() {
        return type;
    }

    public String getRealisticName() {
        return realisticName;
    }

    public void setRealisticName(String realisticName) {
        this.realisticName = realisticName;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public AgeHandler getAgeHandler() {
        return ageHandler;
    }

    public GenderHandler getGenderHandler() {
        return genderHandler;
    }
}
