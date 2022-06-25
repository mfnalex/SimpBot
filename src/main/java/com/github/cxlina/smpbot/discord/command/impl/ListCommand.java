package com.github.cxlina.smpbot.discord.command.impl;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.DiscordCommand;
import de.jeff_media.jefflib.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class ListCommand extends DiscordCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public void run(Member member, MessageChannel channel, Message message, String[] args) {
        Tasks.async(() -> {
            EmbedBuilder e = new EmbedBuilder();
            e.setTitle("All Commands");
            Main.getPlugin().getConfig().getStringList("commands").forEach(command -> e.appendDescription(command + "\n"));
            e.setColor(Color.GREEN);
            channel.sendMessageEmbeds(e.build()).queue();
        });
    }
}