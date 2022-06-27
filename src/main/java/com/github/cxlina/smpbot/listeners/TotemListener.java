package com.github.cxlina.smpbot.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class TotemListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTotem(EntityResurrectEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(player.getCooldown(Material.TOTEM_OF_UNDYING) > 0) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTotem2(EntityResurrectEvent event) {
        if(event.getEntity() instanceof Player player) {
            player.setCooldown(Material.TOTEM_OF_UNDYING, 20 * 20);
        }
    }
}
