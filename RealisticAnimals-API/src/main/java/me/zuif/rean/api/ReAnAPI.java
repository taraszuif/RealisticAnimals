package me.zuif.rean.api;

import me.zuif.rean.api.animal.AnimalManager;
import me.zuif.rean.api.compat.CompatManager;
import me.zuif.rean.api.config.ConfigManager;
import me.zuif.rean.api.desc.DescManager;

public class ReAnAPI {
    private static ReAnAPI instance;
    private final ConfigManager configManager;
    private final DescManager descManager;
    private final AnimalManager animalManager;

    private final CompatManager compatManager;

    public ReAnAPI() {
        configManager = new ConfigManager();
        descManager = new DescManager();
        animalManager = new AnimalManager();
        compatManager = new CompatManager();
    }

    public static ReAnAPI getInstance() {
        if (instance == null) {
            instance = new ReAnAPI();
        }
        return instance;
    }

    public CompatManager getCompatManager() {
        return compatManager;
    }

    public DescManager getDescManager() {
        return descManager;
    }

    public AnimalManager getAnimalManager() {
        return animalManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
