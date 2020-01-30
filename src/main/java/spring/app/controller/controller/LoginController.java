package spring.app.controller.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spring.app.dto.CaptchaResponseDto;
import spring.app.model.User;
import spring.app.util.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

@RestController
public class LoginController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String secret;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserValidator userValidator;

//    @GetMapping("/login")
//    public String getLogin(Model model, String error) {
//        if (error != null) {
//            model.addAttribute("error", "Your username and password is invalid.");
//        }
//        return "login";
//    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, HttpServletRequest request, @ModelAttribute("userForm") User userForm, BindingResult bindingResult) throws IOException, ServletException {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            response.sendRedirect("/login");
        }

        int loginAttempt = 1;
        HttpSession session = request.getSession();

        if (session.getAttribute("loginCount") == null) {
            session.setAttribute("loginCount", 1);
        }

        loginAttempt = (Integer) session.getAttribute("loginCount");

        if (loginAttempt > 3) {
            response.sendRedirect("/login-captcha");
        } else {
            request.getRequestDispatcher("/processing-url").forward(request, response);
        }
    }

    @PostMapping("/login-captcha")
    public void loginCaptcha(
            HttpServletResponse response,
            HttpServletRequest request,
            @RequestParam("g-recaptcha-response") String captchaResponce,
            Model model) throws IOException, ServletException {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);


        if (!captchaResponse.isSuccess()) {
            //        вывод сообщения в html, если капча не введена
            model.addAttribute("captchaError", "Fill captcha");
            response.sendRedirect("/login-captcha");
        } else {
            request.getRequestDispatcher("/processing-url").forward(request, response);
        }
    }
}
