package me.zuif.rean.api.compat;


import org.bukkit.Bukkit;

public class CompatManager {
    private IOverrideHandler overrideHandler;
    private Version version;

    public CompatManager() {
        this.version = Version.UNKNOWN;
        setup();

    }

    public Version getVersion() {
        return version;
    }

    public IOverrideHandler getOverrideHandler() {
        return overrideHandler;
    }


    public void setup() {

        String temp = "";
        try {
            temp = Bukkit.getServer().getClass().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        this.version = Version.parse(temp);
        String overrideClass = "me.zuif.rean.compat." + version.name() + ".utils.OverrideHandler";

        try {
            Class<?> overrideClasz = Class.forName(overrideClass);
            this.overrideHandler = (IOverrideHandler) overrideClasz.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
