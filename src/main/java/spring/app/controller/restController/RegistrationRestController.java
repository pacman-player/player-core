package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.Company;
import spring.app.model.OrgType;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrgTypeService;
import spring.app.service.abstraction.UserService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationRestController {
    private final Logger LOGGER = LoggerFactory.getLogger("RegistrationRestController");
    private UserService userService;
    private CompanyService companyService;
    private OrgTypeService orgTypeService;

    @Autowired
    public RegistrationRestController(UserService userService, CompanyService companyService, OrgTypeService orgTypeService) {
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
    }

    @PostMapping("/first")
    public String saveUser(UserRegistrationDto userDto) {
        User userByEmail = userService.getUserByEmail(userDto.getEmail());
        User userByLogin = userService.getUserByLogin(userDto.getLogin());
        if (userByEmail != null || userByLogin != null) {
            return "exist";
        }
        userService.save(userDto);
        LOGGER.info("Post request 'first', result is {} (DTO)", userDto);
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
        companyService.addCompany(company);
        company = companyService.getByCompanyName(company.getName());
        userByLogin.setCompany(company);
        userService.addUser(userByLogin);
        LOGGER.info("Post request 'second', company is = {} (DTO)", company);
        return "success";
    }

}
