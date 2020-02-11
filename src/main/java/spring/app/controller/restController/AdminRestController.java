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
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger("AdminRestController");
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

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        LOGGER.info("GET request '/all_users'. Result has {} lines", list.size());
        return list;
    }

    @GetMapping("/get_user_by_id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        User user = userService.getUserById(id);
        LOGGER.info("GET request '/get_user_by_id/{}'. Found User = {}", id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_roles")
    public List<Role> getAllRoles() {
        List<Role> list = roleService.getAllRoles();
        LOGGER.info("GET request '/all_roles'. Result has {} lines", list.size());
        return list;
    }

    @GetMapping(value = "/all_companies")
    public @ResponseBody
    List<Company> getAllCompanies() {
        List<Company> list = companyService.getAllCompanies();
        LOGGER.info("GET request '/all_companies'. Result has {} lines", list.size());
        return list;
    }

    @GetMapping(value = "/all_establishments")
    public @ResponseBody
    List<OrgType> getAllEstablishments() {
        List<OrgType> list = orgTypeService.getAllOrgType();
        LOGGER.info("GET request '/all_establishments'. Result has {} lines", list.size());
        return list;
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.addUser(user);
        LOGGER.info("POST request '/add_user'. User login = {}", user);
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        //TODO проверить и убрать строку ниже
        System.out.println(userDto.getRoles());

        User user = new User(userDto.getId(),userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.updateUser(user);
        LOGGER.info("PUT request '/update_user'. User login = {}", user);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteUserById(id);
        LOGGER.info("DELETE request '/delete_user' with id = {}", id);
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany(@PathVariable(value = "id") Long userId) {
        Company company = userService.getUserById(userId).getCompany();
        LOGGER.info("GET request '/company/{}'. Found Company named = {}",
                userId, company.getName());
        return ResponseEntity.ok(company);
    }

    @GetMapping(value = "/companyById/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompanyById(@PathVariable(value = "id") Long companyId) {
        Company company = companyService.getById(companyId);
        LOGGER.info("GET request '/companyById/{}'. Found Company named = {}",
                companyId, company.getName());
        return ResponseEntity.ok(company);
    }

    @PostMapping(value = "/company")
    public void updateUserCompany(@RequestBody CompanyDto companyDto) {
        User userId = new User(companyDto.getUserId());
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getId(), companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
        companyService.updateCompany(company);
        LOGGER.info("POST request '/company'. Updated Company = {}", company);
    }

    @PostMapping(value = "/add_establishment")
    public void addEstablishment(@RequestBody OrgType orgType) {
        orgTypeService.addOrgType(orgType);
        LOGGER.info("POST request '/add_establishment'. Added orgType = {}", orgType);
    }

    @PutMapping(value = "/update_establishment")
    public void updateEstablishment(@RequestBody OrgType orgType) {
        orgTypeService.updateOrgType(orgType);
        LOGGER.info("PUT request '/update_establishment'. Updated orgType = {}", orgType);
    }

    @DeleteMapping(value = "/delete_establishment")
    public void deleteEstablishment(@RequestBody Long id) {
        orgTypeService.deleteOrgTypeById(id);
        LOGGER.info("DELETE request '/delete_establishment' with id = {}", id);
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
        LOGGER.info("POST request '/add_company'. Added Company = {}", company);
    }

    @GetMapping(value = "/check/email")
    public String checkEmail(@RequestParam String email, @RequestParam long id){
        String result = Boolean.toString(userService.isExistUserByEmail(email, id));
        LOGGER.info("GET request '/check/email' with email = {}, id = {}. Result is = {}", email, id, result);
        return result;
    }

    @GetMapping(value = "/check/login")
    public String checkLogin(@RequestParam String login, @RequestParam long id){
       String result = Boolean.toString(userService.isExistUserByLogin(login, id));
        LOGGER.info("GET request '/check/login' with login = {}, id = {}. Result is = {}", login, id, result);
        return result;
    }
}
