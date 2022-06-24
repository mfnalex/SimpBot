package com.github.cxlina.smpbot.discord.command;

import com.github.cxlina.smpbot.discord.command.impl.ListCommand;
import com.github.cxlina.smpbot.discord.command.impl.OnlineCommand;
import com.github.cxlina.smpbot.discord.command.impl.PVPCommand;
import com.github.cxlina.smpbot.discord.command.impl.VerifyCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscordCommandManager extends ListenerAdapter {

    private final List<DiscordCommand> discordCommands;

    public DiscordCommandManager() {
        this.discordCommands = new ArrayList<>();


        this.discordCommands.addAll(Arrays.asList(
                new ListCommand(),
                new OnlineCommand(),
                new VerifyCommand(),
                new PVPCommand()
        ));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot() || e.isWebhookMessage() || !e.getMessage().getContentRaw().startsWith("?")) return;
        String command = e.getMessage().getContentRaw().split(" ")[0].substring(1);
        for (DiscordCommand dc : this.discordCommands) {
            if (command.equals(dc.getName())) {
                String[] args = new String[]{};
                for (String s : e.getMessage().getContentRaw().split(" ")) {
                    if (!s.equalsIgnoreCase("?" + command)) {
                        args = ArrayUtils.add(args, s);
                    }
                }
                dc.run(e.getMember(), e.getChannel(), e.getMessage(), args);
                return;
            }
        }
    }

    public List<DiscordCommand> getDiscordCommands() {
        return discordCommands;
    }
}
