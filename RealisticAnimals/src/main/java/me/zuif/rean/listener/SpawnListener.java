package me.zuif.rean.listener;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.CompatManager;
import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.event.animal.EntityOverridedEvent;
import me.zuif.rean.api.event.animal.EntityPreOverrideEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Optional;

public class SpawnListener implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        LivingEntity livingEntity = event.getEntity();

        CompatManager manager = ReAnAPI.getInstance().getCompatManager();
        IOverrideHandler overrideHandler = manager.getOverrideHandler();
        if (overrideHandler.isRealisticAnimal(livingEntity)) return;

        EntityPreOverrideEvent entityPreOverrideEvent = new EntityPreOverrideEvent(livingEntity);
        Bukkit.getPluginManager().callEvent(entityPreOverrideEvent);
        if (entityPreOverrideEvent.isCancelled()) return;

        Optional<RealisticAnimal> optionalRealisticAnimal = overrideHandler.override(livingEntity);
        if (optionalRealisticAnimal.isEmpty()) return;

        RealisticAnimal result = optionalRealisticAnimal.get();
        EntityOverridedEvent entityOverridedEvent = new EntityOverridedEvent(result);
        Bukkit.getPluginManager().callEvent(entityOverridedEvent);

        event.setCancelled(true);
    }
}
