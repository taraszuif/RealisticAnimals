package me.zuif.rean.api.event.desc;

import me.zuif.rean.api.desc.AnimalCard;
import me.zuif.rean.api.event.CancellableMessaging;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DescShowEvent extends Event implements CancellableMessaging {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private AnimalCard card;
    private boolean cancelled;
    private boolean showCancelMessage;

    public DescShowEvent(Player player, AnimalCard card) {
        this.player = player;
        this.card = card;
        showCancelMessage = true;
    }

    public Player getPlayer() {
        return player;
    }

    public AnimalCard getCard() {
        return card;
    }

    public void setCard(AnimalCard card) {
        this.card = card;
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
