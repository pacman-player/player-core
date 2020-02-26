package spring.app.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/fragment")
public class ThymeleafFragmentController {

    @GetMapping("/admin/navbar")
    public String getAdminNavbar() {
        return "fragment/admin/navbar";
    }
}
