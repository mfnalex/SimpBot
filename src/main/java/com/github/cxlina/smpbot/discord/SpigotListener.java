package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.util.ConfigUtil;
import com.github.cxlina.smpbot.util.TranslationUtil;
import com.github.cxlina.smpbot.util.Util;
import de.jeff_media.jefflib.Tasks;
import de.jeff_media.jefflib.data.AdvancementInfo;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.util.Arrays;

public class SpigotListener implements Listener {

    private static final Main main = Main.getPlugin();

    private boolean isIllegalMention(String message) {
        return Arrays.stream(message.split(" ")).map(String::toLowerCase).anyMatch(s -> s.equals("@everyone") || s.equals("@here"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMinecraftChatMessage(AsyncPlayerChatEvent e) {
        if (isIllegalMention(e.getMessage())) {
            Tasks.nextTick(() -> {
                e.getPlayer().kickPlayer("Stop mentioning @everyone or @here, ya imbecile!");
            });
            return;
        }
        //Handling Discord Chat-Messages
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        String message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', e.getMessage())).replaceAll("§#[0-9a-f]{6}", "");

        Tasks.async(() -> {
            Util.sendWebhookMessageForPlayer(e.getPlayer(), "**" + message + "**");
        });
    }

    @EventHandler
    public void onMinecraftJoin(PlayerJoinEvent e) {
        //Handling Discord Join-Messages
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        Util.sendWebhookEmbedForPlayer(
                e.getPlayer(),
                "Joined the Server.",
                "",
                Color.GREEN
        );
    }

    @EventHandler
    public void onMinecraftQuit(PlayerQuitEvent e) {
        //Handling Discord Quit-Message
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        Util.sendWebhookEmbedForPlayer(
                e.getPlayer(),
                "Left the Server.",
                "",
                Color.RED
        );
    }

    @EventHandler
    public void onMinecraftPlayerDeath(PlayerDeathEvent e) {
        //Handling Discord Death-Message
        Member m = ConfigUtil.getDiscordMember(e.getEntity());
        Entity damager = null;
        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            damager = ((EntityDamageByEntityEvent) e.getEntity().getLastDamageCause()).getDamager();
        }
        Util.sendWebhookEmbedForPlayer(
                e.getPlayer(),
                "Died. [" + TranslationUtil.translateDamageReason(e.getEntity().getLastDamageCause().getCause().name(), damager) + "].",
                "",
                Color.BLACK
        );
    }

    @EventHandler
    public void onMinecraftPlayerAchievement(PlayerAdvancementDoneEvent e) {
        //Handling Discord Advancement-Message
        AdvancementInfo info = new AdvancementInfo(e.getAdvancement());
        if (info.getTitle() == null || e.getAdvancement().getKey().getKey().contains("recipe/")) return;
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        Util.sendWebhookEmbedForPlayer(
                e.getPlayer(),
                "Completed the Advancement " + info.getTitle() + ".",
                "",
                Color.BLACK
        );
    }

    @EventHandler
    public void onMinecraftPlayerHitByPlayer(EntityDamageByEntityEvent e) {
        //Handling PvP-Safemode
        if (e.getEntity() instanceof Player p && e.getDamager() instanceof Player p1) {
            if (!Main.getPlugin().getConfig().getBoolean("pvp." + p.getUniqueId(), true) || !Main.getPlugin().getConfig().getBoolean("pvp." + p1.getUniqueId(), true)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMinecraftPlayerRepairItem(PrepareAnvilEvent e) {
        //Fixing Anvil-Overprice.
        //In vanilla, there's a specific level (dunno which one) after which repairing items is considered as "too expensive" even if players have the xp. this fixes this.
        e.getInventory().setMaximumRepairCost(Integer.MAX_VALUE);
    }
}
