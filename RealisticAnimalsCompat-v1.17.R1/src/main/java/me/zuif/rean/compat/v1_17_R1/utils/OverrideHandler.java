package me.zuif.rean.compat.v1_17_R1.utils;

import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.logger.DebugLogger;
import me.zuif.rean.compat.v1_17_R1.animals.RealisticCow;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import java.util.Optional;


public class OverrideHandler implements IOverrideHandler {


    @Override
    public boolean isRealisticAnimal(LivingEntity entity) {
        return ((CraftEntity) entity).getHandle() instanceof RealisticAnimal;
    }


    @Override
    public Optional<RealisticAnimal> getRealisticAnimal(LivingEntity entity) {
        if (!isRealisticAnimal(entity)) return Optional.empty();
        else return Optional.of((RealisticAnimal) ((CraftEntity) entity).getHandle());
    }

    @Override
    public Optional<RealisticAnimal> override(LivingEntity entity) {
        org.bukkit.entity.EntityType type = entity.getType();
        RealisticCow result = null;

        ServerLevel world = ((CraftWorld) entity.getWorld()).getHandle();

        switch (type) {
            case COW -> result = new RealisticCow(world, entity);
        }
        if (result != null) {
            org.bukkit.Location location = entity.getLocation();

            result.setPos(location.getX(), location.getY(), location.getZ());
            entity.remove();

            world.addFreshEntity(result);

            DebugLogger.log("Overriding " + result.getId());
            return Optional.of(result);
        } else return Optional.empty();
    }
}
