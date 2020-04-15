package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrgTypeService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminRestController.class);
    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final OrgTypeService orgTypeService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, CompanyService companyService,
                               OrgTypeService orgTypeService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
    }

    @PutMapping(value = "/ban_user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bunUser(@PathVariable("id") Long id) {
        LOGGER.info("PUT request '/ban_user/{}'", id);
        User user = userService.getUserById(id);
        user.setEnabled(false);
        userService.updateUser(user);
        LOGGER.info("Banned User = {}", user);
    }

    @PutMapping(value = "/unban_user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbunUser(@PathVariable("id") Long id) {
        LOGGER.info("PUT request '/unban_user/{}'", id);
        User user = userService.getUserById(id);
        user.setEnabled(true);
        userService.updateUser(user);
        LOGGER.info("Unbanned User = {}", user);
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @GetMapping("/get_user_by_id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_roles")
    public List<Role> getAllRoles() {
        List<Role> list = roleService.getAllRoles();
        return list;
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
        List<OrgType> list = orgTypeService.getAllOrgTypes();
        return list;
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        LOGGER.info("POST request '/add_user'");
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.addUser(user);
        LOGGER.info("Added User = {}", user);
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        LOGGER.info("PUT request '/update_user'");
        User user = new User(userDto.getId(), userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.updateUser(user);
        LOGGER.info("Updated User = {}", user);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_user' with id = {}", id);
        userService.deleteUserById(id);
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany(@PathVariable(value = "id") Long userId) {
        Company company = userService.getUserById(userId).getCompany();
        return ResponseEntity.ok(company);
    }

    @GetMapping(value = "/companyById/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompanyById(@PathVariable(value = "id") Long companyId) {
        Company company = companyService.getById(companyId);
        return ResponseEntity.ok(company);
    }

    @PostMapping(value = "/company")
    public void updateUserCompany(@RequestBody CompanyDto companyDto) {
        LOGGER.info("POST request '/company'");
        User userId = new User(companyDto.getUserId());
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getId(), companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
        company.setTariff(companyDto.getTariff());
        companyService.updateCompany(company);
        LOGGER.info("Updated Company = {}", company);
    }

    @PostMapping(value = "/add_establishment")
    public void addEstablishment(@RequestBody OrgType orgType) {
        LOGGER.info("POST request '/add_establishment' with orgType = {}", orgType);
        orgTypeService.addOrgType(orgType);
    }

    @PutMapping(value = "/update_establishment")
    public void updateEstablishment(@RequestBody OrgType orgType) {
        LOGGER.info("PUT request '/update_establishment' with orgType = {}", orgType);
        orgTypeService.updateOrgType(orgType);
    }

    // Returns false if author with requested name already exists else true
    @GetMapping(value = "/establishment/est_type_name_is_free")
    public boolean isLoginFree(@RequestParam("name") String name) {
        boolean isLoginFree = (orgTypeService.getByName(name) == null);
        return isLoginFree;
    }

    @DeleteMapping(value = "/delete_establishment")
    public void deleteEstablishment(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_establishment' with id = {}", id);
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
        LOGGER.info("POST request '/add_company'");
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), null, orgType);
        companyService.addCompany(company);
        LOGGER.info("Added Company = {}", company);
    }

    @GetMapping(value = "/check/email")
    public String checkEmail(@RequestParam String email, @RequestParam long id){
        String result = Boolean.toString(userService.isExistUserByEmail(email, id));
        return result;
    }

    @GetMapping(value = "/check/login")
    public String checkLogin(@RequestParam String login, @RequestParam long id){
        String result = Boolean.toString(userService.isExistUserByLogin(login, id));
        return result;
    }
}
