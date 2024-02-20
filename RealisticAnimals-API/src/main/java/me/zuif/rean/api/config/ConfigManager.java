package me.zuif.rean.api.config;

import me.zuif.rean.api.animal.Animal;
import me.zuif.rean.api.config.file.AnimalsConfig;
import me.zuif.rean.api.config.file.Config;
import me.zuif.rean.api.config.file.LocalizationConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final Map<Animal, AnimalsConfig> animalsConfigs;

    private Config config;
    private LocalizationConfig localizationConfig;

    public ConfigManager() {
        animalsConfigs = new HashMap<>();

        load();
    }

    public Config getConfig() {
        return config;
    }

    public AnimalsConfig getAnimalConfig(Animal type) {
        return animalsConfigs.get(type);
    }

    private void load() {
        for (Animal animal : Animal.values()) {
            AnimalsConfig config = new AnimalsConfig(animal);
            config.reload();
            animalsConfigs.put(animal, config);
        }
        Config config = new Config();
        config.reload();
        LocalizationConfig localizationConfig = new LocalizationConfig(config.getLocale());
        localizationConfig.reload();
        this.localizationConfig = localizationConfig;
        this.config = config;
    }


    public void reloadAll() {
        reloadConfig();
        reloadAnimalsConfigs();
        reloadLocalizationConfig();
    }


    public void saveAll() {
        saveConfig();
        saveAnimalsConfigs();
        saveLocalizationConfig();
    }

    public LocalizationConfig getLocalizationConfig() {
        return localizationConfig;
    }

    public void reloadLocalizationConfig() {
        localizationConfig.reload();
    }

    public void saveLocalizationConfig() {
        localizationConfig.save();
    }


    public void saveConfig() {
        config.save();
    }

    public void sameAnimalsConfig(Animal animal) {
        animalsConfigs.get(animal).save();
    }

    public void saveAnimalsConfigs() {
        for (AnimalsConfig animalsConfig : animalsConfigs.values()) {
            animalsConfig.save();
        }
    }

    public void reloadConfig() {
        config.reload();
    }

    public void reloadAnimalsConfigs() {
        for (AnimalsConfig animalsConfig : animalsConfigs.values()) {
            animalsConfig.reload();
        }
    }

    public void reloadAnimalsConfig(Animal animal) {
        animalsConfigs.get(animal).reload();
    }

}
