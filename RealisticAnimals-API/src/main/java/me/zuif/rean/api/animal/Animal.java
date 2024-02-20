package me.zuif.rean.api.animal;

import org.bukkit.entity.EntityType;

public enum Animal {
    COW("Cow"), PIG("Pig");

    private final String name;

    Animal(String name) {
        this.name = name;
    }

    public static Animal getByType(EntityType type) {
        return switch (type) {
            case COW -> Animal.COW;
            case PIG -> Animal.PIG;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public String getName() {
        return name;
    }

    public String getConfigName() {
        return name.toLowerCase();
    }

    public String getClassName() {
        return "Realistic" + name;
    }
}
