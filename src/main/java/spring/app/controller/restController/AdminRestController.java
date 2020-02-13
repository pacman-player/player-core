package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.dto.UserDto;
import spring.app.model.Company;
import spring.app.model.OrgType;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final GenreService genreService;
    private final OrgTypeService orgTypeService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, CompanyService companyService,
                               GenreService genreService, OrgTypeService orgTypeService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
        this.orgTypeService = orgTypeService;
    }
    @PutMapping(value = "/ban_user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bunUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        user.setEnabled(false);
        userService.updateUser(user);
    }

    @PutMapping(value = "/unban_user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbunUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        user.setEnabled(true);
        userService.updateUser(user);
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @GetMapping("/get_user_by_id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
       return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(value = "/all_companies")
    public @ResponseBody
    List<Company> getAllCompanies() {
        List<Company> list = companyService.getAllCompanies();
        return list;
    }

    @GetMapping(value = "/all_establishments")
    public @ResponseBody
    List<OrgType> getAllEstablishments() {
        List<OrgType> list = orgTypeService.getAllOrgType();
        return list;
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.addUser(user);
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        System.out.println(userDto.getRoles());
        User user = new User(userDto.getId(),userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.updateUser(user);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user.getCompany());
    }

    @GetMapping(value = "/companyById/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompanyById(@PathVariable(value = "id") Long companyId) {
        Company company = companyService.getById(companyId);
        return ResponseEntity.ok(company);
    }

    @PostMapping(value = "/company")
    public void updateUserCompany(@RequestBody CompanyDto companyDto) {
        User userId = new User(companyDto.getUserId());
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getId(), companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
        companyService.updateCompany(company);
    }

    @PostMapping(value = "/add_establishment")
    public void addEstablishment(@RequestBody OrgType orgType) {
        orgTypeService.addOrgType(orgType);
    }

    @PutMapping(value = "/update_establishment")
    public void updateEstablishment(@RequestBody OrgType orgType) {
        orgTypeService.updateOrgType(orgType);
    }

    // Returns false if author with requested name already exists else true
    @GetMapping(value = "/establishment/est_type_name_is_free")
    public boolean isLoginFree(@RequestParam("name") String name) {
        return orgTypeService.getByName(name) == null;
    }

    @DeleteMapping(value = "/delete_establishment")
    public void deleteEstablishment(@RequestBody Long id) {
        orgTypeService.deleteOrgTypeById(id);
    }


    private Set<Role> getRoles(Set<String> role) {
        Set<Role> roles = new HashSet<>();

        for (String rl : role) {
            System.out.println(rl);
            roles.add(roleService.getRoleByName(rl));
        }
        return roles;
    }

    @PostMapping(value = "/add_company")
    public void addCompany(@RequestBody CompanyDto companyDto) {
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), null, orgType);
        companyService.addCompany(company);
    }

    @GetMapping(value = "/check/email")
    public String checkEmail(@RequestParam String email, @RequestParam long id){
        return Boolean.toString(userService.isExistUserByEmail(email, id));
    }

    @GetMapping(value = "/check/login")
    public String checkLogin(@RequestParam String login, @RequestParam long id){
       return Boolean.toString(userService.isExistUserByLogin(login, id));
    }
}
