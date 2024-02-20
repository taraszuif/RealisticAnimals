package me.zuif.rean.command.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReAnTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            result.add("reload");
            result.add("debug");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("debug")) {
                result.addAll(Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).
                        filter(s -> s.startsWith(args[1])).toList());
            }
        }
        return result;
    }
}
