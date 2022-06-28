package com.github.cxlina.smpbot.discord.command.impl;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.time.Duration;

public class MuteCommand extends DiscordCommand {

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public void run(Member member, MessageChannel channel, Message message, String[] args) {
        if (member.hasPermission(Permission.MODERATE_MEMBERS)) {
            if (args.length == 2) {
                if (message.getMentions().getMembers().size() == 1) {
                    Member target = message.getMentions().getMembers().get(0);
                    try {
                        target.timeoutFor(Duration.ofHours(Integer.parseInt(args[1]))).queue();
                        Main.getPlugin().prepareEmbed().title("Muted Member " + target.getEffectiveName()).description("Duration: " + args[1] + " Hours.").color(Color.GREEN).send();
                    } catch (NumberFormatException e) {
                        message.reply("**Please use a valid Integer as a Duration**").queue();
                    }
                } else message.reply("**Please only Mention one user.**").queue();
            } else message.reply("**Please use ?mute <@User> <Duration in Hours>**").queue();
        } else message.reply("**You idiot don't have permissions**").queue();
    }
}
