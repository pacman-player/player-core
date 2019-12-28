package spring.app.controller.restController;

import com.vk.api.sdk.exceptions.ApiMessagesUserBlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.dto.SongDto;
import spring.app.dto.UserDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final GenreService genreService;
    private final SongService songService;
    private final AuthorService authorService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, CompanyService companyService, GenreService genreService, SongService songService, AuthorService authorService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
        this.songService = songService;
        this.authorService = authorService;
    }

    @GetMapping(value = "/all_users")
    public @ResponseBody
    List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @GetMapping(value = "/all_companies")
    public @ResponseBody
    List<Company> getAllCompanies() {
        List<Company> list = companyService.getAllCompanies();
        return list;
    }

    @GetMapping(value = "/all_songs")
    @ResponseBody
    public List<Song> getAllSongs() {
        List<Song> list = songService.getAllSong();
        return list;
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        List<Genre> list = genreService.getAllGenre();
        return list;
    }

    @DeleteMapping(value = "/delete_song")
    public void deleteSong(@RequestBody Long id) {
        songService.deleteSongById(id);
    }

    @PostMapping(value = "/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.addUser(user);
    }

    @PostMapping(value = "/add_song")
    public void addSong(@RequestBody SongDto songDto) {
        Song song = new Song(songDto.getName());
        Author author = authorService.getByName(songDto.getAuthor().getName());
        if (author != null) {
            song.setAuthor(author);
        } else {
            authorService.addAuthor(new Author(songDto.getAuthor().getName()));
            song.setAuthor(authorService.getByName(songDto.getAuthor().getName()));
        }
        Genre genre = genreService.getByName(songDto.getGenre().getName());
        if (genre != null) {
            song.setGenre(genre);
        }
        songService.addSong(song);
    }

    @PutMapping(value = "/update_user")
    public void updateUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getId(),userDto.getEmail(), userDto.getLogin(), userDto.getPassword(), true);
        user.setRoles(getRoles(userDto.getRoles()));
        userService.updateUser(user);
    }

    @PutMapping(value = "/update_song")
    public void updateSong(@RequestBody SongDto songDto) {
        Song oldSong = songService.getSongById(songDto.getId());
        Author author = oldSong.getAuthor();
        Genre genre = genreService.getByName(songDto.getGenre().getName());
        Song song = new Song(songDto.getId(), songDto.getName());
        song.setAuthor(author);
        song.setGenre(genre);
        songService.updateSong(song);
    }

    @GetMapping(value = "/song/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Song> getSongById(@PathVariable(value = "id") Long id) {
        Song song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @DeleteMapping(value = "/delete_user")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getUserCompany(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user.getCompany());
    }

    @PostMapping(value = "/company")
    public void updateUserCompany(@RequestBody CompanyDto companyDto) {
        User userId = new User(companyDto.getUserId());
        OrgType orgType = new OrgType(companyDto.getOrgType());
        Company company = new Company(companyDto.getId(), companyDto.getName(), LocalTime.parse(companyDto.getStartTime()),
                LocalTime.parse(companyDto.getCloseTime()), userId, orgType);
        companyService.updateCompany(company);
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
