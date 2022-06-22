package com.github.cxlina.smpbot.discord.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public interface IDiscordCommand {

    String getName();

    void run(Member member, MessageChannel channel, Message message, String[] args);
}
