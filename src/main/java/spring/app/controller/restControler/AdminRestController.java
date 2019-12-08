package spring.app.controller.restControler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.UserDto;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, GenreService genreService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.addUser(user);
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getId(),userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.updateUser(user);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteUserById(id);
    }




    private Set<Role> getRoles(String role) {
        Set<Role> roles = new HashSet<>();

        switch (role.toLowerCase()) {
            case "admin":
                roles.add(roleService.getRoleById(1L));
                break;
            case "user":
                roles.add(roleService.getRoleById(2L));
                break;
            case "admin, user":
                roles.add(roleService.getRoleById(1L));
                roles.add(roleService.getRoleById(2L));
                break;
            default:
                roles.add(roleService.getRoleById(2L));
                break;
        }
        return roles;
    }
}
