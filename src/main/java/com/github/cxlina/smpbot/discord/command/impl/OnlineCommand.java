package com.github.cxlina.smpbot.discord.command.impl;

import com.github.cxlina.smpbot.discord.command.DiscordCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;

import java.util.stream.Collectors;

public class OnlineCommand extends DiscordCommand {

    @Override
    public String getName() {
        return "online";
    }

    @Override
    public void run(Member member, MessageChannel channel, Message message, String[] args) {
        String s = "**Noone is online!** You're all a bunch of losers \uD83D\uDE22";
        if (Bukkit.getOnlinePlayers().size() > 0) {
            s = (Bukkit.getOnlinePlayers().size() > 1 ? "**There are currently " + Bukkit.getOnlinePlayers().size() + " jerks online** :drooling_face:\n" : "**There's currently 1 jerk online** :drooling_face:\n")
                    + Bukkit.getOnlinePlayers().stream().map(player -> "- " + player.getName()).sorted().collect(Collectors.joining("\n"));
        }
        message.reply(s).queue();
    }
}