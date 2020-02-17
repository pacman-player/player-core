package spring.app.controller.controller;


import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.app.service.abstraction.OrgTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private OrgTypeService orgTypeService;

    @Autowired
    public RegistrationController(OrgTypeService orgTypeService) {
        this.orgTypeService = orgTypeService;
    }

    @GetMapping("/user")
    public String getFirstRegistrationPage(HttpServletRequest request) {
        return "registration/registration-first";
    }

    @GetMapping("/reg_check")
    public String getRegCheckPage(@RequestParam("login") String login, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("login", login);
        return "user/reg_check";
    }

}
