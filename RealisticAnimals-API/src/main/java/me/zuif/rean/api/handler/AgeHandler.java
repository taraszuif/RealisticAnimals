package me.zuif.rean.api.handler;

import me.zuif.rean.api.animal.Age;
import me.zuif.rean.api.config.file.AnimalsConfig;
import me.zuif.rean.api.util.TimeConverter;

public class AgeHandler {
    private final long pregnantEndTime;
    private final boolean pregnant;
    private final ConfigHandler configHandler;
    private long liveTicks;
    private Age age;
    private long deathAge;

    AgeHandler(ConfigHandler handler) {
        this.liveTicks = 0;
        this.deathAge = 10000;
        this.pregnantEndTime = 0;
        this.configHandler = handler;
        this.pregnant = false;
        load();
    }

    public AgeHandler(ConfigHandler configHandler, long pregnantEndTime, boolean pregnant, long liveTicks, Age age, long deathAge) {
        this.configHandler = configHandler;
        this.pregnantEndTime = pregnantEndTime;
        this.pregnant = pregnant;
        this.liveTicks = liveTicks;
        this.age = age;
        this.deathAge = deathAge;
    }

    public long getPregnantEndTime() {
        return pregnantEndTime;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    private void load() {
        AnimalsConfig config = configHandler.getConfig();
        double ageYears = config.getRange(AnimalsConfig.RangeType.SPAWN_AGE).getRandomValue();
        liveTicks = TimeConverter.convertYearsToTicks(ageYears);
        this.age = config.parseAge(liveTicks);
        this.deathAge = TimeConverter.convertYearsToTicks(config.getRange(AnimalsConfig.RangeType.DEATH_AGE).getRandomValue());
    }

    public void tick() {
        liveTicks++;
        Age newAge = configHandler.getConfig().parseAge(liveTicks);
        if (newAge != age) {
            this.age = newAge;
        }
        this.age.setAgeInTicks(liveTicks);
    }


    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public boolean checkIfDead() {
        return liveTicks > deathAge;
    }

    public long getLiveTicks() {
        return liveTicks;
    }

    public void setLiveTicks(long liveTicks) {
        this.liveTicks = liveTicks;
    }

    public long getDeathAge() {
        return deathAge;
    }

    public void setDeathAge(long deathAge) {
        this.deathAge = deathAge;
    }
}
