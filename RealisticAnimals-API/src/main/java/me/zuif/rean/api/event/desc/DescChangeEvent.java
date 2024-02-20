package me.zuif.rean.api.event.desc;

import me.zuif.rean.api.desc.DescBasicField;
import me.zuif.rean.api.event.CancellableMessaging;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DescChangeEvent extends Event implements CancellableMessaging {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final DescBasicField field;
    private final String before;
    private boolean cancelled;
    private String result;
    private boolean showCancelMessage;

    public DescChangeEvent(Player player, DescBasicField field, String before, String result) {
        this.player = player;
        this.field = field;
        this.before = before;
        this.result = result;
        cancelled = false;
        showCancelMessage = true;
    }

    public Player getPlayer() {
        return player;
    }

    public DescBasicField getField() {
        return field;
    }

    public String getBefore() {
        return before;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }


    @Override
    public void showCancelMessage() {
        showCancelMessage = true;
    }

    @Override
    public void hideCancelMessage() {
        showCancelMessage = false;
    }

    @Override
    public boolean isCancelMessageEnabled() {
        return showCancelMessage;
    }
}
