package com.github.cxlina.smpbot.util;

import com.github.cxlina.smpbot.Main;

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
}
