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

//    @GetMapping(value = "/get_user")
//    public User getUserData(@RequestBody User user){
//        return userService.getUserById(id);
//    }

    @PutMapping(value = "/edit_data")
    public void editUserData(@RequestBody User user, String login, String email){
//        long id = ((User) getContext().getAuthentication().getPrincipal()).getCompany().getId();

        user.setLogin(login);
        user.setEmail(email);
        userService.updateUser(user);
    }

    @PutMapping(value = "/edit_pass")
    public void editUserPass(@RequestBody User user, String password){
        user.setPassword(password);
        userService.updateUser(user);
    }
}
