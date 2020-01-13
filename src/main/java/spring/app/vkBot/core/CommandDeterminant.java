package spring.app.vkBot.core;

import com.vk.api.sdk.objects.messages.Message;
import spring.app.vkBot.core.commands.Unknown;

import java.util.Collection;

/**
 * Определяет команду
 */
public class CommandDeterminant {


    public static Command getCommand(Collection<Command> commands, Message message) {
        String body = message.getBody();

        for (Command command : commands
        ) {
                if (command.name.equals(body.split(" ")[0])) {
                    return command;
                }
        }

        return new Unknown("unknown");
    }

}
