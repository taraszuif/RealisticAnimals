package me.zuif.rean.api.handler;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.animal.Animal;
import me.zuif.rean.api.config.file.AnimalsConfig;

public class ConfigHandler {
    private final AnimalsConfig config;
    private final Animal animal;

    ConfigHandler(Animal animal) {
        this.animal = animal;
        this.config = ReAnAPI.getInstance().getConfigManager().getAnimalConfig(animal);
    }

    public AnimalsConfig getConfig() {
        return config;
    }

    public Animal getAnimal() {
        return animal;
    }
}
