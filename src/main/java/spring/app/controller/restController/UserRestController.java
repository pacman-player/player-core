package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;

    private final CompanyService companyService;
    private final GenreService genreService;

    @Autowired
    public UserRestController(RoleService roleService, UserService userService, CompanyService companyService, GenreService genreService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_genre")
    public @ResponseBody
    List<Genre> getAllGenre() {
        return genreService.getAllGenre();
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles(){
        String role = "user";
        User user = (User) getContext().getAuthentication().getPrincipal();
        for (Role roles: user.getRoles()){
            if (roles.getName().equals("ADMIN")){
                role = "admin";
                return role;
            }
        }
        return role;
    }

    @GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany() {
        // long id = ((User) getContext().getAuthentication().getPrincipal()).getId();
        Company company = companyService.getById((long) 1);
        return ResponseEntity.ok(company);
    }

}
