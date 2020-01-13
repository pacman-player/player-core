package spring.app.controller.restController;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.app.vkBot.vk.VKCore;

@RestController
public class VkBotRestController {

    @PostMapping(value = "/admin/vk-bot")
    public void botMessage(@RequestParam String botSendMessage) throws ClientException, ApiException {
        VKCore vkCore = new VKCore();
        vkCore.setMessage(botSendMessage);
        vkCore.sendMessage();
    }
}