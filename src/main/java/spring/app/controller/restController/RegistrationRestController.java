package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.Company;
import spring.app.model.OrgType;
import spring.app.model.PlayList;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrgTypeService;
import spring.app.service.abstraction.PlayListService;
import spring.app.service.abstraction.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/registration")
public class RegistrationRestController {

    private UserService userService;
    private CompanyService companyService;
    private OrgTypeService orgTypeService;
    private PlayListService playListService;

    @Autowired
    public RegistrationRestController(UserService userService, CompanyService companyService, OrgTypeService orgTypeService, PlayListService playListService) {
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.playListService = playListService;
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
        company.setOrgType(orgType);
        company.setUser(userByLogin);

        //сетим утренний плейлист
        PlayList morningPlayList = new PlayList();
        String morningPlaylistName = "morning(" + UUID.randomUUID().toString() + ")";
        morningPlayList.setName(morningPlaylistName);
        playListService.addPlayList(morningPlayList);
        Set<PlayList> morningPlaylistSet = new HashSet<>();
        morningPlaylistSet.add(playListService.getPlayListByName(morningPlaylistName));
        company.setMorningPlayList(morningPlaylistSet);

        //сетим дневной плейлист
        PlayList middayPlayList = new PlayList();
        String middayPlaylistName = "midday(" + UUID.randomUUID().toString() + ")";
        middayPlayList.setName(middayPlaylistName);
        playListService.addPlayList(middayPlayList);
        Set<PlayList> middayPlaylistSet = new HashSet<>();
        middayPlaylistSet.add(playListService.getPlayListByName(middayPlaylistName));
        company.setMiddayPlayList(middayPlaylistSet);

        //сетим вечерний плейлист
        PlayList eveningPlayList = new PlayList();
        String eveningPlaylistName = "evening(" + UUID.randomUUID().toString() + ")";
        eveningPlayList.setName(eveningPlaylistName);
        playListService.addPlayList(eveningPlayList);
        Set<PlayList> eveningPlaylistSet = new HashSet<>();
        eveningPlaylistSet.add(playListService.getPlayListByName(eveningPlaylistName));
        company.setEveningPlayList(eveningPlaylistSet);

        companyService.addCompany(company);
        company = companyService.getByCompanyName(company.getName());

        userByLogin.setCompany(company);
        userService.addUser(userByLogin);
//        Company byCompanyName = companyService.getByCompanyName(company.getName());
//        System.out.println(byCompanyName);
//        if (byCompanyName != null) {
//            return "exist";
//        //return "success";
    }
}
