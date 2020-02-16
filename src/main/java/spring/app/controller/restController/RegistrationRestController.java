package spring.app.controller.restController;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/registration")
public class RegistrationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationRestController.class);
    private UserService userService;
    private CompanyService companyService;
    private OrgTypeService orgTypeService;
    private PlayListService playListService;
    private RoleService roleService;

    @Autowired
    public RegistrationRestController(UserService userService, CompanyService companyService, OrgTypeService orgTypeService, PlayListService playListService, RoleService roleService) {
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.playListService = playListService;
        this.roleService = roleService;
    }

    @PostMapping("/first")
    public void saveUser(UserRegistrationDto userDto) {
        LOGGER.info("POST request '/first' with new User = {}", userDto.getLogin());
        userService.save(userDto);
        LOGGER.info("User registered");
    }

    @GetMapping("/check/email")
    public String checkEmail(@RequestParam String email) {
        LOGGER.info("GET request '/check/email' for email = {}", email);
        boolean isRegistered = userService.isExistUserByEmail(email);
        LOGGER.info("This email is registered = {}", isRegistered);
        return Boolean.toString(!isRegistered);
    }

    @GetMapping("/check/login")
    public String checkLogin(@RequestParam String login) {
        LOGGER.info("GET request '/check/login' for login = {}", login);
        boolean isRegistered = userService.isExistUserByLogin(login);
        LOGGER.info("This login is registered = {}", isRegistered);
        return Boolean.toString(!isRegistered);
    }

    @GetMapping("/check/company")
    public String checkCompany(@RequestParam String name) {
        LOGGER.info("GET request '/check/company' for company name = {}", name);
        boolean isRegistered = companyService.isExistCompanyByName(name);
        LOGGER.info("This company is registered = {}", isRegistered);
        return Boolean.toString(!isRegistered);
    }

    @PostMapping("/second")
    public void saveCompany(Company company, @RequestParam String login) {
        LOGGER.info("POST request '/second' with Company name = {} and User login = {}", company.getName(), login);
        long orgTypeId = Long.parseLong(company.getOrgType().getName()); // ошибка?
        OrgType orgType = orgTypeService.getOrgTypeById(orgTypeId);
        User userByLogin = userService.getUserByLogin(login);
        Role roleUser = roleService.getRoleByName("USER");
        userByLogin.setRoles(Collections.singleton(roleUser));
        company.setOrgType(orgType);
        company.setUser(userByLogin);

        //сетим утренний плейлист
        LOGGER.info("Setting morning playlist...");
        PlayList morningPlayList = new PlayList();
        morningPlayList.setName("Morning playlist");
        playListService.addPlayList(morningPlayList);
        Set<PlayList> morningPlaylistSet = new HashSet<>();
        morningPlaylistSet.add(morningPlayList);
        company.setMorningPlayList(morningPlaylistSet);

        //сетим дневной плейлист
        LOGGER.info("Setting midday playlist...");
        PlayList middayPlayList = new PlayList();
        middayPlayList.setName("Midday playlist");
        playListService.addPlayList(middayPlayList);
        Set<PlayList> middayPlaylistSet = new HashSet<>();
        middayPlaylistSet.add(middayPlayList);
        company.setMiddayPlayList(middayPlaylistSet);

        //сетим вечерний плейлист
        LOGGER.info("Setting evening playlist...");
        PlayList eveningPlayList = new PlayList();
        eveningPlayList.setName("Evening playlist");
        playListService.addPlayList(eveningPlayList);
        Set<PlayList> eveningPlaylistSet = new HashSet<>();
        eveningPlaylistSet.add(eveningPlayList);
        company.setEveningPlayList(eveningPlaylistSet);

        companyService.addCompany(company);
//        company = companyService.getByCompanyName(company.getName());
        LOGGER.info("Adding Company to User...");
        userByLogin.setCompany(company);
        //здесь обновляю недорегенного юзера с уже зашифрованным паролем
        LOGGER.info("Adding User with encode password...");
        userService.addUserWithEncodePassword(userByLogin);
        LOGGER.info("Success!");
//        userService.updateUserWithEncodePassword(userByLogin);
//        Company byCompanyName = companyService.getByCompanyName(company.getName());
//        System.out.println(byCompanyName);
//        if (byCompanyName != null) {
//            return "exist";
//        //return "success";
    }

}
