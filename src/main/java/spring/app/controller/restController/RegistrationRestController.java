package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AddressDto;
import spring.app.dto.CompanyDto;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/registration")
public class RegistrationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationRestController.class);
    private UserService userService;
    private CompanyService companyService;
    private OrgTypeService orgTypeService;
    private PlayListService playListService;
    private RegistrationStepService registrationStepService;
    private AddressService addressService;

    @Autowired
    public RegistrationRestController(UserService userService,
                                      CompanyService companyService,
                                      OrgTypeService orgTypeService,
                                      PlayListService playListService,
                                      RegistrationStepService registrationStepService,
                                      AddressService addressService) {
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.playListService = playListService;
        this.registrationStepService = registrationStepService;
        this.addressService = addressService;
    }

    @PostMapping("/user")
    public void saveUser(UserRegistrationDto userDto) {
        LOGGER.info("POST request '/first' with new User = {}", userDto.getLogin());
        userService.save(userDto);
        User newUser = userService.getUserByLoginWithRegStepsCompany(userDto.getLogin());
        newUser.addRegStep(registrationStepService.getByName("registration-step-user"));
        /*userService.updateUser(newUser);*/
        userService.save(newUser);
        LOGGER.info("User registered");
    }

    @GetMapping("/check/email")
    public String checkEmail(@RequestParam String email) {
        boolean isRegistered = userService.isExistUserByEmail(email);
        return Boolean.toString(!isRegistered);
    }

    @GetMapping("/check/login")
    public String checkLogin(@RequestParam String login) {
        boolean isRegistered = userService.isExistUserByLogin(login);
        return Boolean.toString(!isRegistered);
    }

    @GetMapping("/check/company")
    public String checkCompany(@RequestParam String name) {
        boolean isRegistered = companyService.isExistCompanyByName(name);
        return Boolean.toString(!isRegistered);
    }

    @PostMapping("/company")
    public void saveCompany(@RequestBody CompanyDto companyDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        session.setAttribute("companyName", companyDto.getName());

        LOGGER.info("POST request '/second' with Company name = {} and User login = {}", companyDto.getName(), login);
        long orgTypeId = companyDto.getOrgType();
        OrgType orgType = orgTypeService.getById(orgTypeId);

        User user;
        if (getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            user = userService.getUserByLogin(login);
        } else {
            user = (User) getContext().getAuthentication().getPrincipal();
        }

        Company company = new Company(companyDto.getName(),
                LocalTime.parse(companyDto.getStartTime()), LocalTime.parse(companyDto.getCloseTime()),
                user, orgType);

        companyService.save(company);
        company = companyService.getByCompanyName(companyDto.getName());
        company.setOrgType(orgType);
        company.setUser(user);

        user = userService.getUserByLogin(user.getLogin());

        //сетим утренний плейлист
        PlayList morningPlayList = new PlayList();
        morningPlayList.setName("Morning playlist");
        playListService.save(morningPlayList);
        Set<PlayList> morningPlaylistSet = new HashSet<>();
        morningPlaylistSet.add(morningPlayList);
        company.setMorningPlayList(morningPlaylistSet);

        //сетим дневной плейлист
        PlayList middayPlayList = new PlayList();
        middayPlayList.setName("Midday playlist");
        playListService.save(middayPlayList);
        Set<PlayList> middayPlaylistSet = new HashSet<>();
        middayPlaylistSet.add(middayPlayList);
        company.setMiddayPlayList(middayPlaylistSet);

        //сетим вечерний плейлист
        PlayList eveningPlayList = new PlayList();
        eveningPlayList.setName("Evening playlist");
        playListService.save(eveningPlayList);
        Set<PlayList> eveningPlaylistSet = new HashSet<>();
        eveningPlaylistSet.add(eveningPlayList);
        company.setEveningPlayList(eveningPlaylistSet);

        companyService.update(company);
        company = companyService.getByCompanyName(company.getName());

        LOGGER.info("Adding Company to User...");
        user.setCompany(company);
        //здесь обновляю недорегенного юзера с уже зашифрованным паролем
        LOGGER.info("Adding User with encode password...");
        userService.addUserWithEncodePassword(user);
        LOGGER.info("Success!");

        User newUser = userService.getUserByLogin(user.getLogin());
        newUser.addRegStep(registrationStepService.getByName("registration-step-company"));
        userService.update(newUser);
    }

    //ИСПРАВИТЬ ШИРОТУ И ДОЛГОТУ
    @PostMapping("/address")
    public void saveAddress(@RequestBody AddressDto addressDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        String companyName = (String) session.getAttribute("companyName");

        User user;
        if (getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            user = userService.getUserByLogin(login);
        } else {
            user = (User) getContext().getAuthentication().getPrincipal();
        }

        Address address = new Address();
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHouse(addressDto.getHouse());
        addressService.save(address);


        Company company = companyService.getByCompanyName(companyName);
        LOGGER.info("Adding Address to User...");
        company.setAddress(address);
        companyService.update(company);

        User newUser = userService.getUserByLogin(user.getLogin());
        newUser.addRegStep(registrationStepService.getByName("registration-step-address"));
        userService.update(newUser);
    }

    @GetMapping(value = "/getMissedStepsIds")
    public List<Long> getMissedRegStepsIds(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("login");

        User user;
        if (getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            user = userService.getUserByLogin(userLogin);
        } else {
            user = (User) getContext().getAuthentication().getPrincipal();
        }
        List<Long> stepsIds = registrationStepService.getMissedRegStepsByUserId(user.getId());
        return stepsIds;
    }

    @GetMapping(value = "/getMissedStepsNames")
    public List<String> getMissedRegStepsNames(HttpServletRequest request) {
        List<Long> stepsIds = getMissedRegStepsIds(request);
        List<String> stepsNames = new ArrayList<>();
        for (Long id : stepsIds) {
            stepsNames.add(registrationStepService.getById(id).getName());
        }
        return stepsNames;
    }


    @PostMapping("/getOneStep")
    public ResponseEntity<RegistrationStep> getRegStepToPassNow(@RequestParam Long stepId) {
        RegistrationStep registrationStep = registrationStepService.getById(stepId);
        return ResponseEntity.ok(registrationStep);
    }

    @GetMapping(value = "/get_all_orgTypes")
    public List<OrgType> getAllOrgTypes() {
        return orgTypeService.getAll();
    }
}
