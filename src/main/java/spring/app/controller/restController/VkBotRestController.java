package spring.app.controller.restController;


import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.dto.ConversationDto;
import spring.app.service.impl.VkBotServiceImpl;

@RestController
@RequestMapping(value = "/api/admin/vk_bot")
public class VkBotRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VkBotRestController.class);
    private VkBotServiceImpl vkBotService;

    @Autowired
    public VkBotRestController(VkBotServiceImpl vkBotService) {
        this.vkBotService = vkBotService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody ConversationDto conversationDto) throws ClientException, ApiException {
        LOGGER.info("POST request '/send'");
        try {
            vkBotService.sendMessage(conversationDto.getId(), conversationDto.getMessage());
            LOGGER.info("Success!");
        } catch (ClientException | ApiException e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }


}
