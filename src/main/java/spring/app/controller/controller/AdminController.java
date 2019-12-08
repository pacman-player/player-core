package spring.app.controller.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @GetMapping
    public String getAdminPage(ModelAndView modelAndView) {
        return "admin/admin";
    }
}
