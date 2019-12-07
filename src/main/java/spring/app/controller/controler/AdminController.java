package spring.app.controller.controler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    @GetMapping(value = "/admin")
    public String getAdminPage(ModelAndView modelAndView) {
        return "admin/admin";
    }
}
