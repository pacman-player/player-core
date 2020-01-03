package spring.app.controller.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notification")
    @SendTo("/notification")
    public String getNotification() throws InterruptedException {
        Thread.sleep(500);
        return "str";
    }
}
