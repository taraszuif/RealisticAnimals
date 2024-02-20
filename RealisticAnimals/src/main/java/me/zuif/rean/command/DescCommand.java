package me.zuif.rean.command;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.CompatManager;
import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.config.ConfigManager;
import me.zuif.rean.api.config.file.LocalizationConfig;
import me.zuif.rean.api.desc.DescBasicField;
import me.zuif.rean.api.event.desc.DescChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

public class DescCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        ConfigManager manager = ReAnAPI.getInstance().getConfigManager();
        LocalizationConfig config = manager.getLocalizationConfig();
        if (args.length < 4) {
            player.sendMessage(config.getColorizedMessage(LocalizationConfig.Message.COMMAND_DESC_HELP));
            return true;
        }
        if (!(sender instanceof Player)) {
            player.sendMessage(config.getColorizedMessage(LocalizationConfig.Message.COMMAND_RUN_AS_PLAYER));
            return true;
        }
        if (!player.hasPermission("desc.change")) {
            player.sendMessage(config.getColorizedMessage(LocalizationConfig.Message.NO_PERM));
            return true;
        }
        if (args[0].equalsIgnoreCase("set")) {
            double radius = manager.getConfig().getCardChangeRadius();
            if (args[1].equalsIgnoreCase("realisticname")) {
                String id = args[2];

                boolean notFound = true;
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {

                    CompatManager compatManager = ReAnAPI.getInstance().getCompatManager();
                    IOverrideHandler overrideHandler = compatManager.getOverrideHandler();

                    Optional<RealisticAnimal> realisticAnimalOptional = overrideHandler.getRealisticAnimal((LivingEntity) entity);
                    if (realisticAnimalOptional.isEmpty()) continue;
                    RealisticAnimal realisticAnimal = realisticAnimalOptional.get();

                    if (!realisticAnimal.getRealisticName().equals(id)) {
                        continue;
                    }
                    notFound = false;
                    String before = realisticAnimal.getRealisticName();
                    String after = args[3];

                    DescChangeEvent event = new DescChangeEvent(player, DescBasicField.REALISTIC_NAME, before, after);
                    Bukkit.getPluginManager().callEvent(event);

                    if (event.isCancelled()) {
                        if (event.isCancelMessageEnabled()) {
                            player.sendMessage(config.
                                    getColorizedMessage(LocalizationConfig.Message.COMMAND_DESC_CHANGES_CANCELLED));
                        }
                        return true;
                    }
                    String result = event.getResult();
                    realisticAnimal.setRealisticName(result);

                    player.sendMessage(config.replaceColorizedPlaceholder(
                            (LocalizationConfig.Message.COMMAND_DESC_SET_SUCCESS),
                            Map.of("%before%", before, "%after%", result))
                    );
                }
                if (notFound) {
                    player.sendMessage(config.replaceColorizedPlaceholder(
                            (LocalizationConfig.Message.COMMAND_DESC_CHANGES_CANCELLED),
                            Map.of("%distance", radius))
                    );
                }

                return true;
            }
        }

        return true;
    }
}
