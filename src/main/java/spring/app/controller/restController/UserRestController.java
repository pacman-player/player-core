package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.model.Company;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.*;

import java.time.LocalTime;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;

    private final CompanyService companyService;
    private final GenreService genreService;
    private final SongCompilationService songCompilationService;
    private final SongService songService;

    @Autowired
    public UserRestController(RoleService roleService, UserService userService, CompanyService companyService, GenreService genreService, SongCompilationService songCompilationService, SongService songService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
        this.songService = songService;
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles() {
        String role = "user";
        User user = (User) getContext().getAuthentication().getPrincipal();
        for (Role roles : user.getRoles()) {
            if (roles.getName().equals("ADMIN")) {
                role = "admin";
                return role;
            }
        }
        return role;
    }

    @GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany() {
        long id = ((User) getContext().getAuthentication().getPrincipal()).getCompany().getId();
        return ResponseEntity.ok(companyService.getById(id));
    }

    @PutMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateCompany(@RequestBody CompanyDto company) {
        long id = ((User) getContext().getAuthentication().getPrincipal()).getCompany().getId();
        Company companyForUpdate = companyService.getById(id);
        companyForUpdate.setName(company.getName());
        companyForUpdate.setStartTime(LocalTime.parse(company.getStartTime()));
        companyForUpdate.setCloseTime(LocalTime.parse(company.getCloseTime()));
        companyService.updateCompany(companyForUpdate);
    }

}
