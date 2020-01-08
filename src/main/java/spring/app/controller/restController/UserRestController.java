package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.model.Company;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.*;

import java.time.LocalTime;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    private final CompanyService companyService;

    @Autowired
    public UserRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles() {
        String role = "user";
        User user = (User) getContext().getAuthentication().getPrincipal();
        for (Role roles : user.getRoles()) {
            if (roles.getName().equals("ADMIN")) {
                role = "admin";
                return role;
            }
        }
        return role;
    }

    @GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany() {
        long id = ((User) getContext().getAuthentication().getPrincipal()).getCompany().getId();
        return ResponseEntity.ok(companyService.getById(id));
    }

    @PutMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateCompany(@RequestBody CompanyDto company) {
        long id = ((User) getContext().getAuthentication().getPrincipal()).getCompany().getId();
        Company companyForUpdate = companyService.getById(id);
        companyForUpdate.setName(company.getName());
        companyForUpdate.setStartTime(LocalTime.parse(company.getStartTime()));
        companyForUpdate.setCloseTime(LocalTime.parse(company.getCloseTime()));
        companyService.updateCompany(companyForUpdate);
    }

}
