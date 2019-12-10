package spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import spring.app.model.Role;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller("/test")
public class MainController {

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public MainController(RoleService roleService, UserService userService, GenreService genreService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    /*непонял зачем жтот метод и куда его перенести.
    @RequestMapping(value = {"/p"}, method = RequestMethod.GET)
    public ModelAndView showPlayerPage() throws NoHandlerFoundException {
        return new ModelAndView("player");
    }*/
}
