package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.dto.UserDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, CompanyService companyService, GenreService genreService, AuthorService authorService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @GetMapping(value = "/all_companies")
    public @ResponseBody
    List<Company> getAllCompanies() {
        List<Company> list = companyService.getAllCompanies();
        return list;
    }

    @GetMapping(value = "/all_authors")
    public List<Author> getAllAuthor(){
        List<Author> list = authorService.getAllAuthor();
        return list;
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.addUser(user);
    }

    @PostMapping(value = "/add_author")
    public void addAuthor(@RequestBody String name){
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        if (genreService.getByName(name) == null) {
            Author author = new Author();
            author.setName(name);
            authorService.addAuthor(author);
        }
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getId(),userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.updateUser(user);
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody Author newAuthor){
        Author author = authorService.getById(newAuthor.getId());
        author.setName(newAuthor.getName());
        authorService.updateAuthor(author);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        authorService.deleteAuthorById(id);
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user.getCompany());
    }

    @PostMapping(value = "/company")
    public void updateUserCompany(@RequestBody CompanyDto companyDto) {
        User userId = new User(companyDto.getUserId());
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getId(), companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
        companyService.updateCompany(company);
    }

    private Set<Role> getRoles(String role) {
        Set<Role> roles = new HashSet<>();

        switch (role.toLowerCase()) {
            case "admin":
                roles.add(roleService.getRoleById(1L));
                break;
            case "user":
                roles.add(roleService.getRoleById(2L));
                break;
            case "admin, user":
                roles.add(roleService.getRoleById(1L));
                roles.add(roleService.getRoleById(2L));
                break;
            default:
                roles.add(roleService.getRoleById(2L));
                break;
        }
        return roles;
    }


}
