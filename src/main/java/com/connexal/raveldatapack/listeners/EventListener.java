package com.connexal.raveldatapack.listeners;

import com.connexal.raveldatapack.RavelDatapack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoin(PlayerJoinEvent event) {
        RavelDatapack.getPluginMessageManager().readQueuedPluginMessages();
    }
}
