package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final SongService songService;

    @Autowired
    public UserRestController(RoleService roleService,
                              UserService userService,
                              GenreService genreService,
                              AuthorService authorService,
                              SongService songService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.songService = songService;
    }

    @GetMapping(value = "/all_genre")
    public @ResponseBody
    List<Genre> getAllGenre() {
        List<Genre> genres = genreService.getAllGenre();
        return genres;
    }

    @GetMapping("allAuthors")
    public List<Author> getAllAuthor() {
        return authorService.getAllAuthors();
    }

    @GetMapping("allAuthorsByName/{name}")
    public List<Author> searchByNameInAuthors(@PathVariable String name) {
        return authorService.findAuthorsByNameContaining(name);
    }

    @GetMapping("allSongsByName/{name}")
    public List<Song> searchByNameInSongs(@PathVariable String name) {
        return songService.findSongsByNameContaining(name);
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles(){
        String role = "user";
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Role roles: user.getRoles()){
            if (roles.getName().equals("ADMIN")){
                role = "admin";
                return role;
            }
        }
        return role;
    }
}
