package spring.app.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import spring.app.model.Notification;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public String getNotification(String str) throws InterruptedException {
        System.out.println(str + "!!!!!!!!!!!!!");
    //    Thread.sleep(1000);
        Notification notification = new Notification();
        notification.setMessage("asdfghjkl;");
        //return notification;
        return "!!!!!!!!!!!!!";
    }
}
