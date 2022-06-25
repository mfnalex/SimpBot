package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.util.ConfigUtil;
import com.github.cxlina.smpbot.util.TranslationUtil;
import de.jeff_media.jefflib.Tasks;
import de.jeff_media.jefflib.data.AdvancementInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class SpigotListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMinecraftChatMessage(AsyncPlayerChatEvent e) {
        //Handling Discord Chat-Messages
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessage("**" + (m != null ? m.getEffectiveName() : e.getPlayer().getName()) + "**: " + e.getMessage()).queue(message -> {
                /*
                There doesn't seem to be a reliable method to check whether a role or here or everyone was pinged until the message was actually sent. Pretty stupid.
                Let's just kick the player when it happened. That will at least stop them from spam-pinging.
                 */
                if(message != null) {
                    if(message.getMentions().mentionsEveryone() || message.getMentions().getMentions(Message.MentionType.HERE).size() > 0) {
                        Tasks.nextTick(() -> {
                            e.getPlayer().kickPlayer("Stop mentioning @everyone or @here you imbecile!");
                        });
                    }
                }
            });
        });
    }

    @EventHandler
    public void onMinecraftJoin(PlayerJoinEvent e) {
        //Handling Discord Join-Messages
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setTitle("Simp-SMP").setDescription((m == null ? e.getPlayer().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " joined the Server.");
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
        });
    }

    @EventHandler
    public void onMinecraftQuit(PlayerQuitEvent e) {
        //Handling Discord Quit-Message
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.RED).setTitle("Simp-SMP").setDescription((m == null ? e.getPlayer().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " left the Server.");
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
        });
    }

    @EventHandler
    public void onMinecraftPlayerDeath(PlayerDeathEvent e) {
        //Handling Discord Death-Message
        Member m = ConfigUtil.getDiscordMember(e.getEntity());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setTitle("Simp-SMP").setDescription((m == null ? e.getEntity().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " died. Reason: " + TranslationUtil.translateDamageReason(e.getEntity().getLastDamageCause().getCause().name()) + ".");
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
        });
    }

    @EventHandler
    public void onMinecraftPlayerAchievement(PlayerAdvancementDoneEvent e) {
        //Handling Discord Advancement-Message
        AdvancementInfo info = new AdvancementInfo(e.getAdvancement());
        if (info.getTitle() == null) {
            System.out.println("Null advancement: " + e.getAdvancement().getKey());
            return;
        }
        if (e.getAdvancement().getKey().getKey().contains("recipe/")) return;
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setTitle("Simp-SMP").setDescription((m == null ? e.getPlayer().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " completed the Advancement " + info.getTitle() + ".");
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
        });
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
}
