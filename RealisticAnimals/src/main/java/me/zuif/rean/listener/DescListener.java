package me.zuif.rean.listener;


import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.CompatManager;
import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.config.file.LocalizationConfig;
import me.zuif.rean.api.desc.AnimalCard;
import me.zuif.rean.api.event.desc.DescShowEvent;
import me.zuif.rean.api.logger.DebugLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Optional;

public class DescListener implements Listener {

    @EventHandler
    public void onAnimalClick(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        if (event.getHand() != EquipmentSlot.HAND && event.getHand() == EquipmentSlot.OFF_HAND) return;
        DebugLogger.log("Checking entity " + entity.getType().name() + " uuid: " + entity.getUniqueId() + " entityId: " + entity.getEntityId());
        CompatManager manager = ReAnAPI.getInstance().getCompatManager();
        IOverrideHandler overrideHandler = manager.getOverrideHandler();

        Optional<RealisticAnimal> realisticAnimalOptional = overrideHandler.getRealisticAnimal((LivingEntity) entity);
        if (realisticAnimalOptional.isEmpty()) return;
        RealisticAnimal animal = realisticAnimalOptional.get();

        DebugLogger.log("Found entity " + animal.getRealisticName());
        AnimalCard card = new AnimalCard(animal);
        DescShowEvent descShowEvent = new DescShowEvent(player, card);
        Bukkit.getPluginManager().callEvent(descShowEvent);

        if (descShowEvent.isCancelled()) {
            if (descShowEvent.isCancelMessageEnabled())
                player.sendMessage(ReAnAPI.getInstance().getConfigManager().getLocalizationConfig().
                        getColorizedMessage(LocalizationConfig.Message.CARD_SHOW_CANCELLED));
            return;
        }
        player.spigot().sendMessage(descShowEvent.getCard().getCardMessage());
    }
}
