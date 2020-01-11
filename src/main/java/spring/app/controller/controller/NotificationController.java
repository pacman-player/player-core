package spring.app.controller.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public String getNotification(String str) throws InterruptedException {
        System.out.println(str + "!!!!!!!!!!!!!");
        //    Thread.sleep(1000);
        return "!!!!!!!!!!!!!";
    }
}
