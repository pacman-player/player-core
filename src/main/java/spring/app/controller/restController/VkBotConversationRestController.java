package spring.app.controller.restController;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/vk_bot")
public class VkBotConversationRestController {

    @PostMapping
    public String getVkCallbackAPI(HttpServletRequest request) throws JSONException {

        System.out.println(request.getParameter("type"));
        System.out.println("connect whis VK complid");
        return "8e76e4ad";
    }
}
