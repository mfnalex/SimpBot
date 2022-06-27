package com.github.cxlina.smpbot.util;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.DiscordWebhook;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import org.bukkit.entity.Player;

import java.awt.*;

public class Util {

    public static void sendWebhookMessage(Player p, String author, String message) {
        try {
            TextChannel channel = Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID());
            String s = "";
            for (Webhook h : channel.retrieveWebhooks().complete())
                if (h.getName().equals("Minecraft-Integration")) {
                    s = h.getUrl();
                    break;
                }
            DiscordWebhook hook = new DiscordWebhook(s);
            hook.setUsername(author);
            hook.setAvatarUrl(p == null ? null : "http://cravatar.eu/head/" + p.getUniqueId() + ".png");
            hook.setContent(message);
            hook.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendWebhookEmbed(Player p, String author, String title, String description, Color color) {
        try {
            TextChannel channel = Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID()).getTextChannelById(ConfigUtil.getChatChannelID());
            String s = "";
            for (Webhook h : channel.retrieveWebhooks().complete())
                if (h.getName().equals("Minecraft-Integration")) {
                    s = h.getUrl();
                    break;
                }
            DiscordWebhook hook = new DiscordWebhook(s);
            hook.setUsername(author);
            hook.setAvatarUrl(p == null ? null : "http://cravatar.eu/head/" + p.getUniqueId() + ".png");
            hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(title).setDescription(description).setColor(color));
            hook.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendWebhookMessageForPlayer(Player p, String message) {
        sendWebhookMessage(p, p.getName(), message);
    }

    public static void sendWebhookEmbedForPlayer(Player p, String title, String description, Color color) {
        sendWebhookEmbed(p, p.getName(), title, description, color);
    }
}
