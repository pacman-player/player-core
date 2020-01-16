package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public void saveUser(UserRegistrationDto userDto) {
        userService.save(userDto);
    }

    @GetMapping("/check/email")
    public String checkEmail(@RequestParam String email) {
        if (userService.isExistUserByEmail(email)) {
            return "false";
        }
        return "true";
    }

    @GetMapping("/check/login")
    public String checkLogin(@RequestParam String login) {
        if (userService.isExistUserByLogin(login)) {
            return "false";
        }
        return "true";
    }
    @GetMapping("/check/company")
    public String checkCompany(@RequestParam String name) {
        if (companyService.isExistCompanyByName(name)) {
            return "false";
        }
        return "true";
    }

    @PostMapping("/second")
    public String saveCompany(Company company, @RequestParam String login) {
//        Company byCompanyName = companyService.getByCompanyName(company.getName());
//        System.out.println(byCompanyName);
//        if (byCompanyName != null) {
//            return "exist";
//        }
        long orgTypeId = Long.parseLong(company.getOrgType().getName());
        OrgType orgType = orgTypeService.getOrgTypeById(orgTypeId);
        User userByLogin = userService.getUserByLogin(login);
        company.setOrgType(orgType);
        company.setUser(userByLogin);
        companyService.addCompany(company);
        company = companyService.getByCompanyName(company.getName());
        userByLogin.setCompany(company);
        userService.addUser(userByLogin);
        return "success";
    }

}
