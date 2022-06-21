package com.github.cxlina.smpbot;

import com.github.cxlina.smpbot.discord.Bot;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private Bot bot;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        this.bot = new Bot();
        Bukkit.getScheduler().runTaskTimer(this, () -> bot.getJDA().getPresence().setActivity(Activity.watching("Online: " + Bukkit.getServer().getOnlinePlayers().size())), 0L, 200L);
    }

    @Override
    public void onDisable() {
        this.bot.getJDA().shutdownNow();
    }

    public static Main getPlugin() {
        return plugin;
    }
}