package me.zuif.rean.command;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.config.file.LocalizationConfig;
import me.zuif.rean.api.logger.DebugLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class ReAnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        LocalizationConfig config = ReAnAPI.getInstance().getConfigManager().getLocalizationConfig();
        if (args[0].equalsIgnoreCase("reload")) {
            ReAnAPI.getInstance().getConfigManager().reloadAll();
            sender.sendMessage(config.
                    getColorizedMessage(LocalizationConfig.Message.COMMAND_CONFIG_RELOADED));
            return true;
        } else if (args[0].equalsIgnoreCase("debug")) {
            if (args.length <= 1) {
                String name = sender.getName();
                if (DebugLogger.containsDebugger(name)) {

                    DebugLogger.removeDebugger(name);

                    sender.sendMessage(config.
                            getColorizedMessage(LocalizationConfig.Message.COMMAND_DEBUG_DISABLED_YOU));
                    return true;
                } else {

                    DebugLogger.addDebugger(name);

                    sender.sendMessage(config.
                            getColorizedMessage(LocalizationConfig.Message.COMMAND_DEBUG_ENABLED_YOU));
                    return true;
                }
            } else {
                String name = args[1];
                if (DebugLogger.containsDebugger(name)) {

                    DebugLogger.removeDebugger(name);

                    sender.sendMessage(config.
                            replaceColorizedPlaceholder(LocalizationConfig.Message.COMMAND_DEBUG_DISABLED, Map.of("%player%", name)));
                    return true;
                } else {

                    DebugLogger.removeDebugger(name);

                    sender.sendMessage(config.
                            replaceColorizedPlaceholder(LocalizationConfig.Message.COMMAND_DEBUG_ENABLED, Map.of("%player%", name)));
                    return true;
                }
            }
        }
        return true;
    }
}
