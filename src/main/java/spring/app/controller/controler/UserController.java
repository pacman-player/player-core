package spring.app.controller.controler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping(value = "/user")
    public String getUserPage(ModelAndView modelAndView) {
        return "user/user";
    }
}
