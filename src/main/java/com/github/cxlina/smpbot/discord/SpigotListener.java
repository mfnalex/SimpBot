package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.util.ConfigUtil;
import com.github.cxlina.smpbot.util.TranslationUtil;
import de.jeff_media.jefflib.data.AdvancementInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class SpigotListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMinecraftChatMessage(AsyncPlayerChatEvent e) {
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessage("**" + (m != null ? m.getEffectiveName() : e.getPlayer().getName()) + "**: " + e.getMessage()).queue();
    }

    @EventHandler
    public void onMinecraftJoin(PlayerJoinEvent e) {
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setTitle("Simp-SMP").setDescription((m == null ? e.getPlayer().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " joined the Server.");
        Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
    }

    @EventHandler
    public void onMinecraftQuit(PlayerQuitEvent e) {
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.RED).setTitle("Simp-SMP").setDescription((m == null ? e.getPlayer().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " left the Server.");
        Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
    }

    @EventHandler
    public void onMinecraftPlayerDeath(PlayerDeathEvent e) {
        Member m = ConfigUtil.getDiscordMember(e.getEntity());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setTitle("Simp-SMP").setDescription((m == null ? e.getEntity().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " died. Reason: " + TranslationUtil.translateDamageReason(e.getEntity().getLastDamageCause().getCause().name()) + ".");
        Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
    }

    @EventHandler
    public void onMinecraftPlayerAchievement(PlayerAdvancementDoneEvent e) {
        AdvancementInfo info = new AdvancementInfo(e.getAdvancement());
        if (e.getAdvancement().getKey().getKey().contains("recipe/")) return;
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setTitle("Simp-SMP").setDescription((m == null ? e.getPlayer().getName() : (m.getRoles().isEmpty() ? "" : "[" + m.getRoles().get(0).getName() + "] ") + m.getEffectiveName()) + " completed the Advancement " + info.getTitle() + ".");
        Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessageEmbeds(b.build()).queue();
    }
}
