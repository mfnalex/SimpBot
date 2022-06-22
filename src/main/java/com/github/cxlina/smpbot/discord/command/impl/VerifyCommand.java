package com.github.cxlina.smpbot.discord.command.impl;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.UUID;

public class VerifyCommand extends DiscordCommand {

    @Override
    public String getName() {
        return "verify";
    }

    @Override
    public void run(Member member, MessageChannel channel, Message message, String[] args) {
        if (member.hasPermission(Permission.ADMINISTRATOR)) {
            if (args.length == 2) {
                String id = args[0];
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(args[1]);
                } catch (IllegalArgumentException e) {
                    message.reply("**" + e.getMessage() + "**").queue();
                }
                Main.getPlugin().getConfig().set("verified." + uuid.toString(), id);
                Main.getPlugin().saveConfig();
                message.reply("**Verified successfully.**").queue();
            } else message.reply("**Syntax: ?verify <discord-user-id> <player-uuid>**").queue();
        } else message.reply("**You idiot don't have permissions**").queue();
    }
}
