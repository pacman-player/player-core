package spring.app.controller.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.app.model.Response;
import spring.app.util.ResponseBilder;

import javax.servlet.http.HttpServletRequest;

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
