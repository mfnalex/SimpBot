package com.github.cxlina.smpbot.discord.command.impl;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PVPCommand extends DiscordCommand {

    @Override
    public String getName() {
        return "pvp";
    }

    @Override
    public void run(Member member, MessageChannel channel, Message message, String[] args) {
        if (member.hasPermission(Permission.MANAGE_SERVER)) {
            if (args.length == 1) {
                try {
                    UUID uuid = UUID.fromString(args[0]);
                    boolean flag = Main.getPlugin().getConfig().getBoolean("pvp." + uuid, true);
                    Main.getPlugin().getConfig().set("pvp." + uuid, !flag);
                    Main.getPlugin().saveConfig();
                    message.reply((flag ? "Disabled" : "Enabled") + " PvP for " + Bukkit.getPlayer(uuid).getName()).queue();
                } catch (IllegalArgumentException e) {
                    message.reply("**Invalid UUID format.**").queue();
                } catch (NullPointerException e) {
                    message.reply("**This Player doesn't exist.**").queue();
                }
            }
        }
    }
}
