package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public String saveUser(UserRegistrationDto userDto) {
        User userByEmail = userService.getUserByEmail(userDto.getEmail());
        User userByLogin = userService.getUserByLogin(userDto.getLogin());
        if (userByEmail != null || userByLogin != null) {
            return "exist";
        }
        userService.save(userDto);
        return "success";
    }

    @PostMapping("/second")
    public String saveCompany(Company company, @RequestParam String login) {
        Company byCompanyName = companyService.getByCompanyName(company.getName());
        if (byCompanyName != null) {
            return "exist";
        }
        long orgTypeId = Long.parseLong(company.getOrgType().getName());
        OrgType orgType = orgTypeService.getOrgTypeById(orgTypeId);
        User userByLogin = userService.getUserByLogin(login);
        company.setOrgType(orgType);
        company.setUser(userByLogin);

        //сетим утренний плейлист
        Set<PlayList> morningPlaylistSet = new HashSet<>();
        PlayList morningPlayList = new PlayList();
        playListService.addPlayList(morningPlayList);
        morningPlaylistSet.add(playListService.getPlayList(1L));
        company.setMorningPlayList(morningPlaylistSet);

        //сетим дневной плейлист
        Set<PlayList> middayPlaylistSet = new HashSet<>();
        PlayList middayPlayList = new PlayList();
        playListService.addPlayList(middayPlayList);
        middayPlaylistSet.add(playListService.getPlayList(2L));
        company.setMiddayPlayList(middayPlaylistSet);

        //сетим вечерний плейлист
        Set<PlayList> eveningPlaylistSet = new HashSet<>();
        PlayList eveningPlayList = new PlayList();
        playListService.addPlayList(eveningPlayList);
        eveningPlaylistSet.add(playListService.getPlayList(3L));
        company.setEveningPlayList(eveningPlaylistSet);

        companyService.addCompany(company);
        company = companyService.getByCompanyName(company.getName());

        userByLogin.setCompany(company);
        userService.addUser(userByLogin);
        return "success";
    }

}
