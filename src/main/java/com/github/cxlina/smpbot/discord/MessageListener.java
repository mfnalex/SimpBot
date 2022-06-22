package com.github.cxlina.smpbot.discord;

import com.github.cxlina.smpbot.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    private static final Main main = Main.getPlugin();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
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
}
