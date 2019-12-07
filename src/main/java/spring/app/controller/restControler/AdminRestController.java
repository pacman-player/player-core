package spring.app.controller.restControler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, GenreService genreService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers(){
        List<User> list = userService.getAllUsers();
        return list;
    }
}
