package com.github.cxlina.smpbot.discord.command.impl;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class RetrieveCommand extends DiscordCommand {

    @Override
    public String getName() {
        return "retrieve";
    }

    @Override
    public void run(Member member, MessageChannel channel, Message message, String[] args) {
        if (member.hasPermission(Permission.MANAGE_SERVER)) {
            if (args.length != 1 || message.getMentions().getMembers().isEmpty() || message.getMentions().getMembers().size() != 1) {
                message.reply("Please use **?retrieve <@user>**").queue();
                return;
            }
            Member m = message.getMentions().getMembers().get(0);
            String uuid = "Unknown";
            String id = m.getId();
            for (String s : Main.getPlugin().getConfig().getKeys(true)) {
                if (s.startsWith("verified") && s.split("\\.").length == 2) {
                    if (Main.getPlugin().getConfig().getString(s).equals(m.getId())) {
                        uuid = s.replace("verified.", "");
                        break;
                    }
                }
            }
            Main.getPlugin().prepareEmbed()
                    .title("User-Data from " + m.getUser().getName())
                    .description("UUID: " + uuid + "\nDiscord-ID: " + m.getId())
                    .color(Color.MAGENTA).send();
        } else message.reply("**You idiot don't have permissions**").queue();
    }
}
