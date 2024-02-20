package me.zuif.rean;


import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.config.file.LocalizationConfig;
import me.zuif.rean.api.handler.DataHandler;
import me.zuif.rean.api.util.ObjectPair;
import me.zuif.rean.command.DescCommand;
import me.zuif.rean.command.ReAnCommand;
import me.zuif.rean.command.tabcomplete.DescTabComplete;
import me.zuif.rean.command.tabcomplete.ReAnTabComplete;
import me.zuif.rean.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class RealisticAnimals extends JavaPlugin {
    private static RealisticAnimals instance;
    private static long loadLastTimeUpdateFIle;

    public static RealisticAnimals getInstance() {
        return instance;
    }


    @Override
    public void onDisable() {
        DataHandler.LoadModule.removeAndSaveAll();
        getLogger().info(ReAnAPI.getInstance().getConfigManager().getLocalizationConfig().
                getColorizedMessage(LocalizationConfig.Message.CONSOLE_DISABLED));
    }


    @Override
    public void onEnable() {
        loadCommandHandlers();
        loadListeners();

        ReAnAPI api = ReAnAPI.getInstance();
        ReAnAPI.getInstance().getConfigManager().reloadAll();
        if (api.getConfigManager().getConfig().isDeveloperMode()) {
            developerMode();
        }
        instance = this;
        getLogger().info(ReAnAPI.getInstance().getConfigManager().getLocalizationConfig().
                getColorizedMessage(LocalizationConfig.Message.CONSOLE_ENABLED));
    }

    private void developerMode() {

        int delay = 0;
        int interval = 20; // 1 second

        loadLastTimeUpdateFIle = this.getFile().lastModified();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            long lastModified = this.getFile().lastModified();
            if (loadLastTimeUpdateFIle != lastModified) {
                getLogger().info(ReAnAPI.getInstance().getConfigManager().getLocalizationConfig().
                        getColorizedMessage(LocalizationConfig.Message.CONSOLE_DEV_RESTART));
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "restart");
            }
        }, delay, interval);
    }


    private void loadCommandHandlers() {
        Map<String, ObjectPair<CommandExecutor, TabCompleter>> commands = new HashMap<>();

        commands.put("animals", new ObjectPair<>(new DescCommand(), new DescTabComplete()));
        commands.put("realisticanimals", new ObjectPair<>(new ReAnCommand(), new ReAnTabComplete()));

        for (Map.Entry<String, ObjectPair<CommandExecutor, TabCompleter>> command : commands.entrySet()) {
            PluginCommand pluginCommand = Objects.requireNonNull(getCommand(command.getKey()));

            pluginCommand.setExecutor(command.getValue().getFirst());
            pluginCommand.setTabCompleter(command.getValue().getSecond());

        }

        getLogger().info(ReAnAPI.getInstance().getConfigManager().getLocalizationConfig().
                replaceColorizedPlaceholder(LocalizationConfig.Message.CONSOLE_LOADED_COMMANDS,
                        Map.of("%count%", commands.size())));
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();

        List<Listener> listeners = new ArrayList<>(Arrays.asList(new EntityUnloadListener(), new BreedListener(), new DescListener(),
                new SpawnListener()));

        EntitiesLoadListener entitiesLoadListener = new EntitiesLoadListener();
        listeners.addAll(entitiesLoadListener.getListeners());

        for (Listener listener : listeners) {
            pm.registerEvents(listener, this);
        }

        getLogger().info(ReAnAPI.getInstance().getConfigManager().getLocalizationConfig().
                replaceColorizedPlaceholder(LocalizationConfig.Message.CONSOLE_LOADED_LISTENERS,
                        Map.of("%count%", listeners.size())));
    }
}
