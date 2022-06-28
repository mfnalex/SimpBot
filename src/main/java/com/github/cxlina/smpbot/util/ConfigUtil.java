package com.github.cxlina.smpbot.util;

import com.github.cxlina.smpbot.Main;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;

public class ConfigUtil {

    public static String getToken() {
        return Main.getPlugin().getConfig().getString("application.token", "invalid");
    }

    public static String getMainGuildID() {
        return Main.getPlugin().getConfig().getString("application.guild", "invalid");
    }

    public static String getChatChannelID() {
        return Main.getPlugin().getConfig().getString("application.chat", "invalid");
    }

    public static boolean isVerified(Player p) {
        return Main.getPlugin().getConfig().contains("verified." + p.getUniqueId().toString());
    }

    public static Member getDiscordMember(Player p) {
        if (!isVerified(p)) {
            return null;
        }
        return Main.getPlugin().getBot().getJDA().getGuildById(getMainGuildID()).retrieveMemberById(Main.getPlugin().getConfig().getString("verified." + p.getUniqueId().toString())).complete();
    }

    public static int getConfigVersion() {
        if (!Main.getPlugin().getConfig().contains("version")) {
            Main.getPlugin().getConfig().set("version", 0);
            Main.getPlugin().saveConfig();
        }
        return Main.getPlugin().getConfig().getInt("version");
    }
}
