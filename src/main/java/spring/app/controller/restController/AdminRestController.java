package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.*;
import spring.app.model.*;
import spring.app.service.abstraction.*;
import spring.app.util.ResponseBuilder;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminRestController.class);
    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final OrgTypeService orgTypeService;
    private final ResponseBuilder responseBuilder;
    private final AddressService addressService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, CompanyService companyService,
                               OrgTypeService orgTypeService, ResponseBuilder responseBuilder, AddressService addressService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.responseBuilder = responseBuilder;
        this.addressService = addressService;
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
        if (!companyDto.getName().isEmpty()
                && !companyService.isExistCompanyByName(companyDto.getName())
                || companyService.getByCompanyName(companyDto.getName()).getId().equals(companyDto.getId())) {
        LOGGER.info("POST request '/company'");
        User userId = userService.getById(companyDto.getUserId());
        OrgType orgType = orgTypeService.getById(companyDto.getOrgType());
        Company company = companyService.getById(companyDto.getId());
            company.setName(companyDto.getName());
            company.setStartTime(LocalTime.parse(companyDto.getStartTime()));
            company.setCloseTime(LocalTime.parse(companyDto.getCloseTime()));
            company.setUser(userId);
            company.setOrgType(orgType);
            company.setTariff(companyDto.getTariff());
        companyService.update(company);
        LOGGER.info("Updated Company = {}", company);
        }

    }

    @GetMapping(value = "/check/company")
    public boolean isTypeNameFree(@RequestParam("name") String name,
                                  @RequestParam("id") Long id) {
        if (companyService.getById(id).getName().equals(name)) {
            return true;
        }
        return !companyService.isExistCompanyByName(name);
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

    @GetMapping(value = "/all_roles")
    public Response getAllRoles() {
        Response response = responseBuilder.success(roleService.getAllRolesDto());
        return response;
    }

    @DeleteMapping(value = "/delete_role")
    public void deleteRole(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_role' with id = {}", id);
        roleService.deleteById(id);
    }

    @PostMapping(value = "/add_role")
    public void addRole(@RequestBody Role role) {
        LOGGER.info("POST request '/add_role' with role = {}", role);
        roleService.save(role);
    }

    @PutMapping(value = "/update_role")
    public void updateRole(@RequestBody Role role) {
        LOGGER.info("PUT request '/update_role' with role = {}", role);
        roleService.update(role);
    }

    // Returns false if author with requested name already exists else true
    @GetMapping(value = "/role/est_type_name_is_free")
    public Response<T> isLoginFreeRole(@RequestParam("name") String name) {
        Boolean isRoleDto;
        if (roleService.getRoleDtoByName(name) == null) {
            isRoleDto = true;
        } else isRoleDto = false;
        return responseBuilder.success(isRoleDto);
    }


    private Set<Role> getRoles(Set<String> role) {
        Set<Role> roles = new HashSet<>();
        for (String rl : role) {
            roles.add(roleService.getRoleByName(rl));
        }
        return roles;
    }

    @PostMapping(value = "/getRoleRequest")
    private Response<T> getRoleRequest() {
        List<RoleDto> roles = roleService.getAllRolesDto();
        return responseBuilder.success(roles);
    }

    @PostMapping(value = "/add_company", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addCompany(@RequestBody
                                       CompanyDto companyDto) {
        if (!companyService.isExistCompanyByName(companyDto.getName())) {
            OrgType orgType = new OrgType(companyDto.getOrgType());
            User userId;
            Company company;
            if (companyDto.getUserId() == null) {
                userId = null;
            } else {
                userId = userService.getById(companyDto.getUserId());
            }
            if (!(companyDto.getAddressCity() == null)) {
                addressService.save(new Address(
                        companyDto.getAddressCountry(),
                        companyDto.getAddressCity(),
                        companyDto.getAddressStreet(),
                        companyDto.getAddressHouse(),
                        companyDto.getAddressLatitude(),
                        companyDto.getAddressLongitude()
                ));
                company = new Company(companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                        LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
                company.setTariff(companyDto.getTariff());
                company.setAddress(addressService.getById(addressService.getLastId()));
            } else {
                company = new Company(companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                        LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
                company.setTariff(companyDto.getTariff());
            }
            companyService.save(company);
        }
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
