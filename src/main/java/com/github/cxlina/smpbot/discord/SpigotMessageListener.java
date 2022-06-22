package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.util.ConfigUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.awt.*;

public class SpigotMessageListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMinecraftChatMessage(AsyncPlayerChatEvent e) {
        MessageEmbed.Field f = new MessageEmbed.Field(e.getPlayer().getName(), e.getMessage(), true);
        EmbedBuilder embed = new EmbedBuilder().setColor(Color.GREEN).setTitle("SMP-Chat").addField(f);
        Member m = ConfigUtil.getDiscordMember(e.getPlayer());
        String s = this.stripMentionHexCodes(e.getMessage().replace("&", "§"));
        String finalMessage = this.stripMentionHexCodes(e.getMessage());

        Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID()).sendMessage("**" + (m != null ? m.getEffectiveName() : e.getPlayer().getName()) + "**: " + finalMessage).queue();
    }

    private String stripMentionHexCodes(String text) {
        String[] args = text.split("§#");
        StringBuilder b = new StringBuilder();
        for (String arg : args) {
            if (arg.equals("@"))
                continue;
            b.append(arg.charAt(6));
        }
        return b.toString();
    }
}
