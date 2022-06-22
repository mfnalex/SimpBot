package com.github.cxlina.smpbot.discord.command.commands;

import com.github.cxlina.smpbot.discord.command.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.Bukkit;

import java.util.stream.Collectors;

public class OnlineCommand implements Command {

    @Override
    public void onCommand(Member user, Message message, String[] args) {

        String reply = "**Noone is online!** You're all a bunch of losers \uD83D\uDE22";
        if (Bukkit.getOnlinePlayers().size() > 0) {
            reply = (Bukkit.getOnlinePlayers().size() > 1 ? "**There are currently " + Bukkit.getOnlinePlayers().size() + " jerks online** :drooling_face:\n" : "**There's currently 1 jerk online** :drooling_face:\n")
                    + Bukkit.getOnlinePlayers().stream().map(player -> "- " + player.getName()).sorted().collect(Collectors.joining("\n"));
        }

        message.reply(reply).queue();
    }
}
