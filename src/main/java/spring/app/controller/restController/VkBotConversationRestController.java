package spring.app.controller.restController;


import com.vk.api.sdk.objects.messages.Message;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/vk_bot")
public class VkBotConversationRestController {

    Message message = new Message();

    @PostMapping
    public String getVkCallbackAPI(HttpServletRequest request) throws JSONException {

        System.out.println(request.getParameter("message"));
        System.out.println("connect whis VK complid");
        return "8e76e4ad";
    }

    @GetMapping
    public String getVkMessage(HttpServletRequest request) throws JSONException {

        System.out.println(request.getParameter("type"));
        System.out.println("asdadsadsadsadasd");
        return "8e76e4ad";
    }
}
