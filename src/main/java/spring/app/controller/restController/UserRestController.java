package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.model.*;
import spring.app.service.CutSongService;
import spring.app.service.EmailPasswordGeneration;
import spring.app.service.EmailSender;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.Random;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;

    private final CompanyService companyService;

    private String PASSWORD = "";

    @Autowired
    public EmailSender emailSender;

    @Autowired
    public UserRestController(RoleService roleService,
                              UserService userService,
                              CompanyService companyService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping(value = "/get_user")
    public User getUserData(){
        return ((User) getContext().getAuthentication().getPrincipal());
    }

    @PutMapping(value = "/edit_data")
    public ResponseEntity<User> editUserData(@RequestBody User newUser){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        if(!newUser.getLogin().equals(user.getLogin())) {
            if (userService.getUserByLogin(newUser.getLogin()) == null) {
                user.setLogin(newUser.getLogin());
            }else{
                return ResponseEntity.badRequest().body(user);
            }
        }
        if(!newUser.getEmail().equals(user.getEmail())){
            if(userService.getUserByEmail(newUser.getEmail()) == null){
                user.setEmail(newUser.getEmail());
            }else{
                return ResponseEntity.badRequest().body(user);
            }
        }
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/edit_pass")
    public void editUserPass(@RequestBody String newPassword){
        newPassword = newPassword.substring(1, newPassword.length()-1);
        newPassword = newPassword.replaceAll("##@##"  , "\"");
        newPassword = newPassword.replaceAll("##@@##"  ,"\\\\");

        User user = ((User) getContext().getAuthentication().getPrincipal());
        user.setPassword(newPassword);
        userService.updateUser(user);
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

    @PutMapping(value = "/code_check")
    public ResponseEntity<String> codeCheck(@RequestBody String code){
        code = code.substring(1, code.length()-1);
        if(code.equals(PASSWORD)){
            return ResponseEntity.ok("Пароль совпадает");
        }
        return ResponseEntity.badRequest().body("Пароль не совпадает");
    }

    @PutMapping(value = "/send_mail")
    public void sendMail(){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        EmailPasswordGeneration emailPasswordGeneration = new EmailPasswordGeneration();
        PASSWORD = emailPasswordGeneration.generate();
        System.out.println(PASSWORD);
        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Для смены пароля введите код подтверждения: " + PASSWORD + "\n " +
                        "Если вы не запрашивали смену пароля, свяжитесь со службой технической поддержки.",
                user.getLogin()
        );

        if(user.getEmail() != null && !user.getEmail().equals("user@gmail.com") && !user.getEmail().equals("admin@gmail.com")) {
            emailSender.send(user.getEmail(), "Смена пароля", message);
        }

    }

}
