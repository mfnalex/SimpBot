package com.github.cxlina.smpbot.discord.command.commands;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class ListCommand implements Command {

    @Override
    public void onCommand(Member user, Message message, String[] args) {

        EmbedBuilder e = new EmbedBuilder();
        e.setTitle("All Commands");
        Main.getPlugin().getConfig().getStringList("commands").forEach(command -> e.appendDescription(command + "\n"));
        e.setColor(Color.GREEN);
        message.getChannel().sendMessageEmbeds(e.build()).queue();
    }
}
