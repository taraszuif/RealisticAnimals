package me.zuif.rean.command.tabcomplete;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.CompatManager;
import me.zuif.rean.api.compat.IOverrideHandler;
import me.zuif.rean.api.compat.RealisticAnimal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DescTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            result.add("set");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                result.add("realisticname");
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("realisticname")) {
                double radius = ReAnAPI.getInstance().getConfigManager().getConfig().getCardChangeRadius();
                if (!(sender instanceof Player player)) return result;
                CompatManager manager = ReAnAPI.getInstance().getCompatManager();
                IOverrideHandler overrideHandler = manager.getOverrideHandler();

                result.addAll(player.getNearbyEntities(radius, radius, radius).stream().
                        filter(entity -> overrideHandler.isRealisticAnimal((LivingEntity) entity)).
                        map(animal -> ((RealisticAnimal) animal).getRealisticName()).toList());
            }
        }
        return result;
    }
}
