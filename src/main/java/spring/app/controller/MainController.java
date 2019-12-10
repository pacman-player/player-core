package spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import spring.app.model.Role;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller("/test")
public class MainController {

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public MainController(RoleService roleService, UserService userService, GenreService genreService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
    }


	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String redirectToLoginPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws NoHandlerFoundException {

		return new ModelAndView("login");
	}

	@RequestMapping(value = {"/translation"}, method = RequestMethod.GET)
	public ModelAndView showPlayerPage() throws NoHandlerFoundException {

		return new ModelAndView("translation");
	}

	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public ModelAndView getAdminPage(HttpSession httpSession) throws NoHandlerFoundException {
		List<User> users = userService.getAllUsers();

		ModelAndView model = new ModelAndView("admin");
		model.addObject("users", users);

		return model;
	}

	@RequestMapping(value = {"/admin/addUser"}, method = RequestMethod.POST)
	public String addUser(@RequestParam("email") String email, @RequestParam("login") String login,
						  @RequestParam("password") String password,
						  @RequestParam("role") String role) {

		User user = new User(email, login, password, true);
		user.setRoles(getRoles(role));

		userService.addUser(user);

		return "redirect:/admin";
	}

	@RequestMapping(value = {"/admin/edit"}, method = RequestMethod.POST)
	public String editUser(@RequestParam("id") Long id, @RequestParam("email") String email,
						   @RequestParam("login") String login,
						   @RequestParam("password") String password,
						   @RequestParam("role") String role, HttpSession httpSession) {

		User user = new User(id, email, login, password, true);

 


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
