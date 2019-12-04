package spring.app.configuration.initializator;

import org.springframework.beans.factory.annotation.Autowired;
import spring.app.model.Genre;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.HashSet;
import java.util.Set;


public class TestDataInit {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private GenreService genreService;


	private void init() throws Exception {

		Role roleAdmin = new Role();
		roleAdmin.setName("ADMIN");
		roleService.addRole(roleAdmin);

		Role roleUser = new Role();
		roleUser.setName("USER");
		roleService.addRole(roleUser);

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@gmail.com");
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(roleAdmin);
		adminRoles.add(roleUser);
		admin.setRoles(adminRoles);

		userService.addUser(admin);

		User user = new User();
		user.setLogin("user");
		user.setPassword("user");
		user.setEmail("user@gmail.com");
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(roleUser);
		user.setRoles(userRoles);

		userService.addUser(user);

		Genre rock =new Genre();
		rock.setName("rock");

		genreService.addGenre(rock);

		Genre pop =new Genre();
		pop.setName("pop");

		genreService.addGenre(pop);

		Genre classic =new Genre();
		classic.setName("classic");

		genreService.addGenre(classic);

		Genre rep =new Genre();
		rep.setName("rep");

		genreService.addGenre(rep);

		Genre retro =new Genre();
		retro.setName("retro");

		genreService.addGenre(retro);
	}
}
