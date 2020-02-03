package spring.app.controller.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.app.service.abstraction.OrgTypeService;

import javax.servlet.http.Cookie;
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

    @GetMapping("/first")
    public String getFirstRegistrationPage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("firstRegistrationStepDone")) {
                    return "redirect:/registration/second?login=" + cookie.getValue();
                }
//                if (cookie.getName().equals("secondRegistrationStepDone")) {
//                    return "redirect:/login";
//                }
            }
        }
        return "registration/registration-first";
    }

    @GetMapping("/second")
    public String getSecondRegistrationPage(@RequestParam String login, Model model, HttpServletResponse response) {
        response.addCookie(new Cookie("firstRegistrationStepDone", login));
        model.addAttribute("login", login);
        model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
        return "registration/registration-second";
    }

    //для недорегенных пользователей - логин получаем с формы логина в сессии
    @GetMapping("/preuser")
    public String getSecondRegistrationPagePreuser(Model model, HttpServletResponse response, HttpSession httpSession) {
        String loginFromLoginController = (String) httpSession.getAttribute("login");
        response.addCookie(new Cookie("firstRegistrationStepDone", loginFromLoginController));
        model.addAttribute("login", loginFromLoginController);
        model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
        return "registration/registration-second";
    }

    @GetMapping("/end")
    public String endOfRegistration(@RequestParam String login, HttpServletResponse response) {
        Cookie cookie = new Cookie("firstRegistrationStepDone", login);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "login";
    }
}
