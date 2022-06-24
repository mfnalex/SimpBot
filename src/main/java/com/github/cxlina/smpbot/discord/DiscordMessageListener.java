package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import com.github.cxlina.smpbot.util.ConfigUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordMessageListener extends ListenerAdapter {

    private static final Main main = Main.getPlugin();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        onDiscordChatMessage(event);
        translateCumWords(event);
        onPing(event);
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

    private void onDiscordChatMessage(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getMessage().isWebhookMessage() || !event.getChannel().getId().equals(ConfigUtil.getChatChannelID()) || event.getMessage().getContentRaw().startsWith("?"))
            return;
        StringBuilder builder = new StringBuilder();
        for (String s : event.getMessage().getContentRaw().split(" ")) {
            if (s.startsWith("<@") && s.endsWith(">")) {
                try {
                    builder.append(event.getJDA().getGuildById(ConfigUtil.getMainGuildID()).retrieveMemberById(s.substring(2, s.length() - 1)).complete().getEffectiveName());
                } catch (IllegalArgumentException e) {
                    builder.append(s, 2, s.length() - 1).append(" "); // if the bot couldn't get the mentioned User from the id some reason, it'll just use the id itself instead. only used when errors happen.
                }
                continue;
            }
            builder.append(s).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        Bukkit.broadcastMessage("§7[§9Discord§7] §8" + (event.getAuthor().getName()) + " §7§l» §r" + builder.toString());
    }
}
