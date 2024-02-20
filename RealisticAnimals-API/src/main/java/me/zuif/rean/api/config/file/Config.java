package me.zuif.rean.api.config.file;

import me.zuif.rean.api.config.wrapper.ConfigBoolean;
import me.zuif.rean.api.config.wrapper.ConfigDouble;
import me.zuif.rean.api.config.wrapper.ConfigList;
import me.zuif.rean.api.config.wrapper.ConfigString;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends AbstractConfiguration {
    private ConfigString locale;
    private ConfigDouble timeRatio;
    private ConfigList<String> onRightClick;
    private ConfigDouble cardChangeRadius;
    private ConfigBoolean developerMode;

    public Config() {
        super("/options/", "config.yml", true);
    }

    public String getLocale() {
        return locale.getValue();
    }

    public void setLocale(String locale) {
        this.locale.setValue(locale);
    }

    public double getTimeRatio() {
        return timeRatio.getValue();
    }

    public void setTimeRatio(double timeRatio) {
        this.timeRatio.setValue(timeRatio);
    }

    public double getCardChangeRadius() {
        return cardChangeRadius.getValue();
    }

    public void setCardChangeRadius(double cardChangeRadius) {
        this.cardChangeRadius.setValue(cardChangeRadius);
    }

    public boolean isDeveloperMode() {
        return developerMode.getValue();
    }

    public void setDeveloperMode(boolean developerMode) {
        this.developerMode.setValue(developerMode);
    }

    @Override
    public void reload() {
        super.reload();
        YamlConfiguration cfg = getYamlConfiguration();
        developerMode = new ConfigBoolean(cfg.getBoolean("developer-mode"), "developer-mode");
        locale = new ConfigString("locale", cfg.getString("locale"));
        timeRatio = new ConfigDouble(cfg.getDouble("time-ratio"), "time-ratio");
        onRightClick = new ConfigList<>("on-right-click", cfg.getStringList("on-right-click"));
        cardChangeRadius = new ConfigDouble(cfg.getDouble("card-change-radius"), "card-change-radius");
    }

    @Override
    public void save() {
        YamlConfiguration cfg = getYamlConfiguration();
        cfg.set(locale.getPath(), locale.getValue());
        cfg.set(timeRatio.getPath(), timeRatio.getValue());
        cfg.set(onRightClick.getPath(), onRightClick.getList());
        cfg.set(cardChangeRadius.getPath(), cardChangeRadius.getValue());
        super.save();
    }
}
