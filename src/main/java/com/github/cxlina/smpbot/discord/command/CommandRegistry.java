package com.github.cxlina.smpbot.discord.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry {

    private final Map<String, Command> commandMap = new HashMap<>();

    public void register(String name, Command command) {
        commandMap.put(name, command);
    }

    public Optional<Command> getCommandByName(String name) {
        return Optional.ofNullable(commandMap.get(name));
    }

}
