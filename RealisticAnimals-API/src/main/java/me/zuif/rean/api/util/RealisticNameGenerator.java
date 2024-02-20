package me.zuif.rean.api.util;

import org.bukkit.entity.Entity;

public class RealisticNameGenerator {
    private Entity entity;

    public RealisticNameGenerator(Entity entity) {
        this.entity = entity;
    }

    public String generate() {
        return entity.getType().name() + "-" + entity.getEntityId();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
