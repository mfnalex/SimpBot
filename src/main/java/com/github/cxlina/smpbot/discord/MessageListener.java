package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.discord.command.CommandRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Locale;
import java.util.stream.Collectors;

public class MessageListener extends ListenerAdapter {

    private static final String PREFIX = "?";
    private static final Main main = Main.getPlugin();

    private final CommandRegistry commandRegistry;

    public MessageListener(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        if(!message.startsWith(PREFIX)) {
            onPing(event);
            translateCumWords(event);
            return;
        }

        String[] args = message.split(" ");
        String command = args[0].replaceFirst("\\?", "");

        commandRegistry.getCommandByName(command).ifPresent(cmd -> {

            String[] newArgs = new String[args.length-1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);

            cmd.onCommand(event.getMember(), event.getMessage(), newArgs);
        });
    }

    private void onPing(MessageReceivedEvent event) {
        Message referenced = event.getMessage().getReferencedMessage();
        if (referenced == null) return;
        if (!referenced.getAuthor().equals(main.getBot().getJDA().getSelfUser())) return;
        if (referenced.getAuthor().equals(event.getAuthor())) return;
        event.getMessage().reply(main.getReplyManager().getReply()).queue();
    }

    private void translateCumWords(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String[] split = message.split(" ");
        String cumWord = null;
        for (String word : split) {
            cumWord = main.getCumManager().getCumTranslation(word);
            if (cumWord != null) {
                break;
            }
        }
        if (cumWord == null) {
            return;
        }
        event.getMessage().reply(cumWord).queue();
    }
}
