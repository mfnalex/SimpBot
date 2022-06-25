package com.github.cxlina.smpbot;

import com.github.cxlina.smpbot.discord.Bot;
import com.github.cxlina.smpbot.discord.SpigotListener;
import com.github.cxlina.smpbot.util.ConfigUtil;
import com.github.cxlina.smpbot.util.CumManager;
import com.github.cxlina.smpbot.util.ReplyManager;
import de.jeff_media.jefflib.Tasks;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private Bot bot;
    private CumManager cumManager;
    private ReplyManager replyManager;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        this.bot.getJDA().shutdownNow();
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        this.cumManager = new CumManager(Objects.requireNonNull(getConfig().getConfigurationSection("cum-translations")));
        this.replyManager = new ReplyManager(getConfig().getStringList("on-ping"));
        this.bot = new Bot();
        Bukkit.getPluginManager().registerEvents(new SpigotListener(), this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            try {
                bot.getJDA().getPresence().setActivity(Activity.watching("Online: " + Bukkit.getServer().getOnlinePlayers().size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0L, 200L);
    }

    public Bot getBot() {
        return bot;
    }

    public CumManager getCumManager() {
        return cumManager;
    }

    public ReplyManager getReplyManager() {
        return replyManager;
    }

    public EmbedBuilder prepareEmbed() {
        return new EmbedBuilder();
    }

    public static final class EmbedBuilder {

        private String title = "Simp-SMP";
        private String channelId = ConfigUtil.getChatChannelID();
        private String description;
        private Color color = Color.GREEN;

        public EmbedBuilder title(String title) {
            this.title = title;
            return this;
        }

        public EmbedBuilder channelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public EmbedBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EmbedBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public void send() {
            Tasks.async(() -> {
                Guild guild = Main.getPlugin().getBot().getJDA().getGuildById(ConfigUtil.getMainGuildID());
                if (guild == null) {
                    Main.getPlugin().getLogger().warning("[JDA] Could not find guild with id " + ConfigUtil.getMainGuildID());
                    return;
                }
                TextChannel channel = guild.getTextChannelById(channelId);
                if (channel == null) {
                    Main.getPlugin().getLogger().warning("[JDA] Could not find channel with id " + channelId);
                    return;
                }
                channel.sendMessageEmbeds(new net.dv8tion.jda.api.EmbedBuilder().setTitle(title).setDescription(description).setColor(color).build()).queue();
            });
        }


    }
}