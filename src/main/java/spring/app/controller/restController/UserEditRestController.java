package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.model.User;
import spring.app.service.abstraction.UserService;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;


@RestController
@RequestMapping(value = "/api/user/edit/")
public class UserEditRestController {

    private final UserService userService;

    @Autowired
    public UserEditRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get_user")
    public User getUserData(){
        return ((User) getContext().getAuthentication().getPrincipal());
    }

    @PutMapping(value = "/edit_data")
    public void editUserData(@RequestBody User newUser){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        user.setLogin(newUser.getLogin());
        user.setEmail(newUser.getEmail());
        userService.updateUser(user);
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
}
