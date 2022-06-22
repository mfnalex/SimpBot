package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
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

    private static final Main main = Main.getPlugin();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        translateCumWords(event);
        parseCommands(event);
        onPing(event);
    }

    private void onPing(MessageReceivedEvent event) {
        Message referenced = event.getMessage().getReferencedMessage();
        if (referenced == null) return;
        if (!referenced.getAuthor().equals(main.getBot().getJDA().getSelfUser())) return;
        if (referenced.getAuthor().equals(event.getAuthor())) return;
        event.getMessage().reply(main.getReplyManager().getReply()).queue();
    }

    private void parseCommands(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().split(" ")[0];
        if (!message.startsWith("?")) return;
        String command = message.substring(1).toLowerCase(Locale.ROOT);
        switch (command) {
            case "list":
                this.sendCommandList(event.getChannel());
                break;
            case "online":
                onList(event);
                break;
        }
    }

    private void onList(MessageReceivedEvent event) {
        String message = "**Noone is online!** You're all a bunch of losers \uD83D\uDE22";
        if (Bukkit.getOnlinePlayers().size() > 0) {
            message = (Bukkit.getOnlinePlayers().size() > 1 ? "**There are currently " + Bukkit.getOnlinePlayers().size() + " jerks online** :drooling_face:\n" : "**There's currently 1 jerk online** :drooling_face:\n")
                    + Bukkit.getOnlinePlayers().stream().map(player -> "- " + player.getName()).sorted().collect(Collectors.joining("\n"));
        }
        event.getMessage().reply(message).queue();
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

    private void sendCommandList(MessageChannel channel) {
        EmbedBuilder e = new EmbedBuilder();
        e.setTitle("All Commands");
        main.getConfig().getStringList("commands").forEach(command -> e.appendDescription(command + "\n"));
        e.setColor(Color.GREEN);
        channel.sendMessageEmbeds(e.build()).queue();
    }
}
