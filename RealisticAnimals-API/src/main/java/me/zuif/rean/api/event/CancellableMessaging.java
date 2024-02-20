package me.zuif.rean.api.event;

import org.bukkit.event.Cancellable;

public interface CancellableMessaging extends Cancellable {
    void showCancelMessage();

    void hideCancelMessage();

    boolean isCancelMessageEnabled();
}
