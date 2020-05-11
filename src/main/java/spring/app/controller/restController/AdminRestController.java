package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import spring.app.dto.CompanyDto;
import spring.app.dto.OrgTypeDto;
import spring.app.dto.RoleDto;
import spring.app.dto.UserDto;
import spring.app.model.*;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrgTypeService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;
import spring.app.util.ResponseBilder;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

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
        User user = userService.getById(id);
        user.setEnabled(false);
        userService.update(user);
        LOGGER.info("Banned User = {}", user);
    }

    @PutMapping(value = "/unban_user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbunUser(@PathVariable("id") Long id) {
        LOGGER.info("PUT request '/unban_user/{}'", id);
        User user = userService.getById(id);
        user.setEnabled(true);
        userService.update(user);
        LOGGER.info("Unbanned User = {}", user);
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<UserDto> getAllUsers() {
        List<UserDto> list = userService.getAllUsers();

        return list;
    }

    @GetMapping("/get_user_by_id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_roles")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRolesDto();
    }

    @GetMapping(value = "/all_companies")
    public @ResponseBody
    List<CompanyDto> getAllCompanies() {
        List<CompanyDto> list = companyService.getAllCompanies();

        return list;
    }

    @GetMapping(value = "/all_establishments")
    public @ResponseBody
    List<OrgTypeDto> getAllEstablishments() {
        List<OrgTypeDto> list = orgTypeService.getAllOrgTypeDto();
        return list;
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        LOGGER.info("POST request '/add_user'");
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.save(user);
        LOGGER.info("Added User = {}", user);
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        LOGGER.info("PUT request '/update_user'");
        User user = new User(userDto.getId(), userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        // If admin is editing himself, then we need put his renewed account
        // in SecurityContext
        User userAuth = (User) getContext().getAuthentication().getPrincipal();
        if (userAuth.getId().equals(user.getId())) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            user.getPassword(),
                            user.getAuthorities()
                    );
            getContext().setAuthentication(token);
        }
        userService.update(user);
        LOGGER.info("Updated User = {}", user);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_user' with id = {}", id);
        userService.deleteById(id);
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany(@PathVariable(value = "id") Long userId) {
        Company company = userService.getById(userId).getCompany();
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
        companyService.update(company);
        LOGGER.info("Updated Company = {}", company);
    }

    @PostMapping(value = "/add_establishment")
    public void addEstablishment(@RequestBody OrgType orgType) {
        LOGGER.info("POST request '/add_establishment' with orgType = {}", orgType);
        orgTypeService.save(orgType);
    }

    @PutMapping(value = "/update_establishment")
    public void updateEstablishment(@RequestBody OrgType orgType) {
        LOGGER.info("PUT request '/update_establishment' with orgType = {}", orgType);
        orgTypeService.update(orgType);
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
        orgTypeService.deleteById(id);
    }


    private Set<Role> getRoles(Set<String> role) {
        Set<Role> roles = new HashSet<>();

        for (String rl : role) {
            System.out.println(rl);
            roles.add(roleService.getByName(rl));
        }
        return roles;
    }

    @PostMapping(value = "/getRoleRequest")
    private Response getRoleRequest(WebRequest request) {
        ResponseBilder responseBilder = new ResponseBilder();
        List<RoleDto> roles = roleService.getAllRolesDto();
        Response response = responseBilder.success(roles, request);
        return response;
    }

    @PostMapping(value = "/add_company")
    public void addCompany(@RequestBody CompanyDto companyDto) {
        LOGGER.info("POST request '/add_company'");
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), null, orgType);
        companyService.save(company);
        LOGGER.info("Added Company = {}", company);
    }

    @GetMapping(value = "/check/email")
    public String checkEmail(@RequestParam String email, @RequestParam long id) {
        return Boolean.toString(userService.isExistUserByEmail(email));
    }

    @GetMapping(value = "/check/login")
    public String checkLogin(@RequestParam String login, @RequestParam long id) {
        return Boolean.toString(userService.isExistUserByLogin(login));
    }
}
