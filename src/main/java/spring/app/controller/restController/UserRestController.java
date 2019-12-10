package spring.app.controller.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Genre;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;

    private final GenreService genreService;

    @Autowired
    public UserRestController(RoleService roleService, UserService userService, GenreService genreService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_genre")
    public @ResponseBody
    List<Genre> getAllGenre() {
        List<Genre> genres = genreService.getAllGenre();
        return genres;
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles(){
        String role = "user";
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Role roles: user.getRoles()){
            if (roles.getName().equals("ADMIN")){
                role = "admin";
                return role;
            }
        }
        return role;
    }
}
