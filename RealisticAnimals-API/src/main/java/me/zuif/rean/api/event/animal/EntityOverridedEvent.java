package me.zuif.rean.api.event.animal;

import me.zuif.rean.api.compat.RealisticAnimal;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityOverridedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final RealisticAnimal realisticAnimal;

    public EntityOverridedEvent(RealisticAnimal realisticAnimal) {
        this.realisticAnimal = realisticAnimal;
    }

    public RealisticAnimal getRealisticAnimal() {
        return realisticAnimal;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
