package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.DiscordCommandManager;
import com.github.cxlina.smpbot.util.ConfigUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private JDA jda;

    static {
        /*
        JDA calls net/dv8tion/jda/internal/managers/AudioManagerImpl on shutdown. This class isn't loaded though
        and the Bukkit PluginClassLoader doesn't allow loading new classes in onDisable, so we'll Class.forName it right
        now.
         */
        try {
            Class.forName("net.dv8tion.jda.internal.managers.AudioManagerImpl");
        } catch (Throwable ignored) { };
    }

    public Bot() {
        if (ConfigUtil.getToken().equals("invalid")) return;
        try {
            this.jda = JDABuilder.createDefault(ConfigUtil.getToken())
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new DiscordCommandManager())
                    .build().awaitReady();
            Main.getPlugin().getLogger().info("SMPBot Started.");
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public JDA getJDA() {
        return jda;
    }
}
