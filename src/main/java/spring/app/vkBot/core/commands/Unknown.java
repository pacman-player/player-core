package spring.app.vkBot.core.commands;

import com.vk.api.sdk.objects.messages.Message;
import spring.app.vkBot.core.Command;
import spring.app.vkBot.vk.VKManager;

public class Unknown extends Command {

    public Unknown(String name) {
        super(name);
    }

    @Override
    public void exec(Message message) {
        new VKManager().sendMessage("Неизвестная команда", message.getUserId());
    }
}
