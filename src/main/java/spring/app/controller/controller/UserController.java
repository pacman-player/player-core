package spring.app.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/fileUpload")
    public String fileUpload() {
        return "user/fileUpload";

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping
    public String getUserPage() {
        return "user/user";
    }
}
