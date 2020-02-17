package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/registration")
public class RegistrationRestController {

    private UserService userService;
    private CompanyService companyService;
    private OrgTypeService orgTypeService;
    private PlayListService playListService;
    private RoleService roleService;
    private UserCompanyService userCompanyService;
    private RegistrationStepService registrationStepService;

    @Autowired
    public RegistrationRestController(
            UserService userService, CompanyService companyService, OrgTypeService orgTypeService,
            PlayListService playListService, RoleService roleService, UserCompanyService userCompanyService,
            RegistrationStepService registrationStepService) {
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.playListService = playListService;
        this.roleService = roleService;
        this.userCompanyService = userCompanyService;
        this.registrationStepService = registrationStepService;
    }

    @PostMapping("/first")
    public void saveUser(UserRegistrationDto userDto) {
        userService.save(userDto);
        User newUser = userService.getUserByLogin(userDto.getLogin());
        UserCompany userCompany = new UserCompany(newUser.getId(), 0L,  1L);
        userCompanyService.save(userCompany);
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
    public void saveCompany(@RequestBody CompanyDto companyDto/*Company company*/, HttpServletRequest request) {
        long orgTypeId = companyDto.getOrgType();
        //long orgTypeId = Long.parseLong(company.getOrgType().getName());
        OrgType orgType = orgTypeService.getOrgTypeById(orgTypeId);

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        User user;
        if (getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            user = userService.getUserByLogin(login);
        } else {
            user = (User) getContext().getAuthentication().getPrincipal();
        }

        Role roleUser = roleService.getRoleByName("USER");
        user.setRoles(Collections.singleton(roleUser));

        Company company = companyService.getByCompanyName(companyDto.getName());
        company.setOrgType(orgType);
        company.setUser(user);

        user = userService.getUserByLogin(user.getLogin());

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
        company = companyService.getByCompanyName(company.getName());

        user.setCompany(company);
        //здесь обновляю недорегенного юзера с уже зашифрованным паролем
        userService.addUserWithEncodePassword(user);

        UserCompany userCompany = new UserCompany(user.getId(), company.getId(), 2L);
        userCompanyService.save(userCompany);
//        userService.updateUserWithEncodePassword(userByLogin);
//        Company byCompanyName = companyService.getByCompanyName(company.getName());
//        System.out.println(byCompanyName);
//        if (byCompanyName != null) {
//            return "exist";
//        //return "success";
    }

    //ИСПРАВИТЬ: поиск только по Логин
    @PostMapping ("/getPages")
    public ResponseEntity<List<Long>> getMissedRegSteps(@RequestParam String login) {

        User user = userService.getUserByLogin(login);
        List<Long> regStepsToPass = userCompanyService.getMissedRegSteps(user.getId());

        return ResponseEntity.ok(regStepsToPass);
    }

    @PostMapping ("/getOneStep")
    public ResponseEntity<RegistrationStep> getRegStepToPassNow(@RequestParam Long stepId) {
        RegistrationStep registrationStep = registrationStepService.getRegStepById(stepId);
        return ResponseEntity.ok(registrationStep);
    }

}
