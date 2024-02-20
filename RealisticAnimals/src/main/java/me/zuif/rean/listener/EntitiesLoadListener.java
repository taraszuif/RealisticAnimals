package me.zuif.rean.listener;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.handler.DataHandler;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntitiesLoadListener {
    private final List<Listener> listeners;


    public EntitiesLoadListener() {
        listeners = new ArrayList<>();

        try {
            Class.forName("com.destroystokyo.paper.event.entity.EntityAddToWorldEvent");
            listeners.add(new Listener() {
                @EventHandler
                private void onEntitiesLoad(EntityAddToWorldEvent event) {
                    processAnimal(event.getEntity());
                }
            });
        } catch (ClassNotFoundException ignored) {
        }

        try {
            Class.forName("org.bukkit.event.world.EntitiesLoadEvent");
            listeners.add(new Listener() {
                @EventHandler
                private void onEntitiesLoad(EntitiesLoadEvent event) {
                    for (Entity ent : event.getEntities()) {
                        processAnimal(ent);
                    }
                }
            });
        } catch (ClassNotFoundException e2) {
            listeners.add(new Listener() {
                @EventHandler
                private void onChunkLoad(ChunkLoadEvent event) {
                    for (Entity ent : event.getChunk().getEntities()) {
                        processAnimal(ent);
                    }
                }
            });
        } finally {
            listeners.add(new Listener() {
                @EventHandler
                private void onChunkLoad(WorldLoadEvent event) {
                    for (Entity ent : event.getWorld().getEntitiesByClass(Animals.class)) {
                        processAnimal(ent);
                    }
                }
            });
        }

    }

    public List<Listener> getListeners() {
        return listeners;
    }

    private void processAnimal(Entity entity) {
        if (!(entity instanceof Animals)) return;
        IOverrideHandler handler = ReAnAPI.getInstance().getCompatManager().getOverrideHandler();
        if (handler.isRealisticAnimal((LivingEntity) entity)) return;
        Optional<RealisticAnimal> animalOptional = ReAnAPI.getInstance().getCompatManager().getOverrideHandler().override((LivingEntity) entity);
        if (animalOptional.isPresent()) {
            RealisticAnimal animal = animalOptional.get();
            DataHandler.LoadModule.addLoaded(animal.getID());
        }
    }
}
