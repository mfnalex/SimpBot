package com.github.cxlina.smpbot.discord.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public interface Command {

    void onCommand(Member user, Message message, String[] args);

}
