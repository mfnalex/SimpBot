package com.github.cxlina.smpbot;

import com.github.cxlina.smpbot.discord.Bot;
import com.github.cxlina.smpbot.discord.SpigotMessageListener;
import com.github.cxlina.smpbot.util.CumManager;
import com.github.cxlina.smpbot.util.ReplyManager;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private Bot bot;
    private CumManager cumManager;
    private ReplyManager replyManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        this.cumManager = new CumManager(Objects.requireNonNull(getConfig().getConfigurationSection("cum-translations")));
        this.replyManager = new ReplyManager(getConfig().getStringList("on-ping"));
        this.bot = new Bot();
        Bukkit.getPluginManager().registerEvents(new SpigotMessageListener(), this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            try {
                bot.getJDA().getPresence().setActivity(Activity.watching("Online: " + Bukkit.getServer().getOnlinePlayers().size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0L, 200L);
    }

    @Override
    public void onDisable() {
        this.bot.getJDA().shutdownNow();
    }

    public static Main getPlugin() {
        return plugin;
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
}