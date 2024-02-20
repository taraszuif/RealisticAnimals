package me.zuif.rean.api.compat;


import org.bukkit.entity.LivingEntity;

import java.util.Optional;

public interface IOverrideHandler {
    boolean isRealisticAnimal(LivingEntity entity);


    Optional<RealisticAnimal> getRealisticAnimal(LivingEntity entity);

    Optional<RealisticAnimal> override(LivingEntity entity);
}
