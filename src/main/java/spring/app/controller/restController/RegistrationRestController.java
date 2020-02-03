package spring.app.controller.restController;

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
        userService.save(userDto);
    }

    @GetMapping("/check/email")
    public String checkEmail(@RequestParam String email) {
        return Boolean.toString(!userService.isExistUserByEmail(email));
    }

    @GetMapping("/check/login")
    public String checkLogin(@RequestParam String login) {
        return Boolean.toString(!userService.isExistUserByLogin(login));
    }
    @GetMapping("/check/company")
    public String checkCompany(@RequestParam String name) {
        return Boolean.toString(!companyService.isExistCompanyByName(name));
    }

    @PostMapping("/second")
    public void saveCompany(Company company, @RequestParam String login) {
        long orgTypeId = Long.parseLong(company.getOrgType().getName());
        OrgType orgType = orgTypeService.getOrgTypeById(orgTypeId);
        User userByLogin = userService.getUserByLogin(login);
        Role roleUser = roleService.getRoleByName("USER");
        userByLogin.setRoles(Collections.singleton(roleUser));
        company.setOrgType(orgType);
        company.setUser(userByLogin);

        //сетим утренний плейлист
        PlayList morningPlayList = new PlayList();
        morningPlayList.setName("Morning playlist");
        playListService.addPlayList(morningPlayList);
        Set<PlayList> morningPlaylistSet = new HashSet<>();
        morningPlaylistSet.add(morningPlayList);
        company.setMorningPlayList(morningPlaylistSet);

        //сетим дневной плейлист
        PlayList middayPlayList = new PlayList();
        middayPlayList.setName("Midday playlist");
        playListService.addPlayList(middayPlayList);
        Set<PlayList> middayPlaylistSet = new HashSet<>();
        middayPlaylistSet.add(middayPlayList);
        company.setMiddayPlayList(middayPlaylistSet);

        //сетим вечерний плейлист
        PlayList eveningPlayList = new PlayList();
        eveningPlayList.setName("Evening playlist");
        playListService.addPlayList(eveningPlayList);
        Set<PlayList> eveningPlaylistSet = new HashSet<>();
        eveningPlaylistSet.add(eveningPlayList);
        company.setEveningPlayList(eveningPlaylistSet);

        companyService.addCompany(company);
//        company = companyService.getByCompanyName(company.getName());

        userByLogin.setCompany(company);
        userService.addUserWithEncodePassword(userByLogin);
//        userService.updateUserWithEncodePassword(userByLogin);
//        Company byCompanyName = companyService.getByCompanyName(company.getName());
//        System.out.println(byCompanyName);
//        if (byCompanyName != null) {
//            return "exist";
//        //return "success";
    }
}
