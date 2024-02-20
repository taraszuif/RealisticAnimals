package me.zuif.rean.api.handler;

import me.zuif.rean.api.animal.Trust;
import me.zuif.rean.api.config.file.AnimalsConfig;

public class TrustHandler {
    private final ConfigHandler handler;
    private Trust trust;

    TrustHandler(ConfigHandler handler) {
        this.handler = handler;

    }

    public void load() {
        this.trust = new Trust(handler.getConfig().getRange(AnimalsConfig.RangeType.SPAWN_TRUST_VALUE).getRandomValue(),
                handler.getConfig().getRange(AnimalsConfig.RangeType.TRUST));
    }
}
