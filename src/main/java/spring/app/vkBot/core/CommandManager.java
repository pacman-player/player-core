package spring.app.vkBot.core;

import spring.app.vkBot.core.commands.Unknown;

import java.util.HashSet;

public class CommandManager {
    private static HashSet<Command> commands = new HashSet<>();

    static {
        commands.add(new Unknown("unknown"));
    }

    public static HashSet<Command> getCommands(){
        return commands;
    }
    public static void addCommand(Command command) {
        commands.add(command);
    }
}
