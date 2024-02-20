package me.zuif.rean.api.logger;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.config.file.LocalizationConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DebugLogger {
    private static final List<String> messageList = new ArrayList<>();

    {
        messageList.add("Zuif");
    }

    public static void log(String message) {
        message = LocalizationConfig.MessagesUtils.colorize("&a[ReAnDebug] &e" + message);
        Bukkit.broadcastMessage(message);
        boolean developerMode = ReAnAPI.getInstance().getConfigManager().getConfig().isDeveloperMode();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (messageList.contains(player.getName()) || (developerMode && player.isOp())) {
                player.sendMessage(message);
            }
        }
    }

    public static boolean containsDebugger(String name) {
        return messageList.contains(name);
    }

    public static void addDebugger(String name) {
        messageList.add(name);
    }

    public static void removeDebugger(String name) {
        messageList.remove(name);
    }
}
