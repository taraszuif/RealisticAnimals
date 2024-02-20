package me.zuif.rean.listener;

import me.zuif.rean.api.handler.DataHandler;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesUnloadEvent;

public class EntityUnloadListener implements Listener {

    @EventHandler
    public void onUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities()) {
            processUnload(entity);
        }
    }

    public void processUnload(Entity entity) {
        DataHandler.LoadModule.removeAndSave(entity.getUniqueId());
    }
}
