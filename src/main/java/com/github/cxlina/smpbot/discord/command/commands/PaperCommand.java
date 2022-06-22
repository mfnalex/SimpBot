package com.github.cxlina.smpbot.discord.command.commands;

import com.github.cxlina.smpbot.discord.command.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PaperCommand implements Command {

    @Override
    public void onCommand(Member user, Message message, String[] args) {

        File file = getResourceAsFile("images/paper.jpg");

        if(file != null)
            message.reply(file).queue();
        else
            message.reply("Picture not found, shows how garbage it is.").queue();
    }

    private static File getResourceAsFile(String resourcePath) {

        try {

            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in == null)
                return null;

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1)
                    out.write(buffer, 0, bytesRead);
            }

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
