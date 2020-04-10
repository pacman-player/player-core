package core.app.controller.controller;


import core.app.model.User;
import core.app.service.abstraction.OrgTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

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

    //попадаем сюда из reg_check.js
    @GetMapping("/end")
    public String endRegistration(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        User user;
        if (getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/login";

        } else {
            return "redirect:/user/spa"; //переходим на Single Page Application
            //return "redirect:/user/statistics";
        }
    }
}
