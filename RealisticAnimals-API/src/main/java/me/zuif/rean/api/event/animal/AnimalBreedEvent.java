package me.zuif.rean.api.event.animal;

import me.zuif.rean.api.compat.RealisticAnimal;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class AnimalBreedEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final RealisticAnimal mother;
    private final RealisticAnimal father;
    private final ItemStack breedItem;
    private boolean genderCompatible;
    private boolean cancelled;

    public AnimalBreedEvent(RealisticAnimal mother, RealisticAnimal father, boolean genderCompatible, ItemStack breedItem) {
        this.mother = mother;
        this.father = father;
        this.genderCompatible = genderCompatible;
        this.breedItem = breedItem;
    }

    public RealisticAnimal getMother() {
        return mother;
    }

    public RealisticAnimal getFather() {
        return father;
    }

    public boolean isGenderCompatible() {
        return genderCompatible;
    }

    public void setGenderCompatible(boolean genderCompatible) {
        this.genderCompatible = genderCompatible;
    }

    public ItemStack getBreedItem() {
        return breedItem;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
