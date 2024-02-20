package me.zuif.rean.listener;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.CompatManager;
import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.event.animal.AnimalBreedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class BreedListener implements Listener {

    @EventHandler
    public void onBreed(EntityBreedEvent event) {
        LivingEntity first = event.getBreeder();
        LivingEntity second = event.getEntity();
        ItemStack breedItem = event.getBredWith();

        CompatManager manager = ReAnAPI.getInstance().getCompatManager();
        IOverrideHandler overrideHandler = manager.getOverrideHandler();

        Optional<RealisticAnimal> realisticAnimalFatherOptional = overrideHandler.getRealisticAnimal(first);
        if (realisticAnimalFatherOptional.isEmpty()) return;
        RealisticAnimal father = realisticAnimalFatherOptional.get();

        Optional<RealisticAnimal> realisticAnimalMotherOptional = overrideHandler.getRealisticAnimal(second);
        if (realisticAnimalMotherOptional.isEmpty()) return;
        RealisticAnimal mother = realisticAnimalMotherOptional.get();

        boolean compatibility = ReAnAPI.getInstance().getConfigManager().getAnimalConfig(father.getRealisticType()).
                checkCompatibility(father, mother);

        AnimalBreedEvent animalBreedEvent = new AnimalBreedEvent(mother, father, compatibility, breedItem);
        Bukkit.getPluginManager().callEvent(animalBreedEvent);
        if (animalBreedEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
