package spring.app.controller.restController;

import com.vk.api.sdk.actions.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.CompanyDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final SongService songService;
    private final CompanyService companyService;
    private final SongCompilationService songCompilation;

    @Autowired
    public UserRestController(RoleService roleService,
                              UserService userService,
                              CompanyService companyService,
                              GenreService genreService,
                              AuthorService authorService,
                              SongService songService,
                              SongCompilationService songCompilation) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.songService = songService;
        this.companyService = companyService;
        this.songCompilation = songCompilation;
    }

    @PostMapping(value = "/song_compilation")
    public @ResponseBody
    List<SongCompilation> getSongCompilation(@RequestBody String genre) {
        genre = genre.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genre.equals("Все подборки")) {
            return songCompilation.getAllSongCompilations();
        } else {
            Genre genres = genreService.getByName(genre);
            List<SongCompilation> list = songCompilation.getListSongCompilationsByGenreId(genres.getId());
            return songCompilation.getListSongCompilationsByGenreId(genres.getId());
        }
    }

    @GetMapping("allSongsByName/{name}")
    public List<Song> searchByNameInSongs(@PathVariable String name,
                                          @AuthenticationPrincipal User user) {
        List<Song> songs = songService.findSongsByNameContaining(name);

        companyService.checkAndMarkAllBlockedByTheCompany(user.getCompany(), songs);
        return songs;
    }

    @PostMapping("authorsBan")
    public void addAuthorInBan(@AuthenticationPrincipal User user,
                               @RequestBody long authorsId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedAuthor(authorService.getById(authorsId));

        companyService.updateCompany(company);
        user.setCompany(company);
    }

    @PostMapping("authorsUnBan")
    public void authorUnBan(@AuthenticationPrincipal User user,
                            @RequestBody long authorsId) {
        Company company = user.getCompany();
        company.getBannedAuthor().removeIf(author -> author.getId().equals(authorsId));
        companyService.updateCompany(company);

        user.setCompany(company);
    }

    @PostMapping("songsBan")
    public void addSongInBan(@AuthenticationPrincipal User user,
                             @RequestBody long songId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedSong(songService.getById(songId));

        companyService.updateCompany(company);
        user.setCompany(company);
    }

    @PostMapping("songsUnBan")
    public void songUnBan(@AuthenticationPrincipal User user,
                          @RequestBody long songId) {
        Company company = user.getCompany();
        company.getBannedSong().removeIf(song -> song.getId().equals(songId));
        companyService.updateCompany(company);

        user.setCompany(company);
    }

    @PostMapping("genreBan")
    public void addGenreInBan(@AuthenticationPrincipal User user,
                              @RequestBody long genreId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedGenre(genreService.getById(genreId));

        companyService.updateCompany(company);
        user.setCompany(company);
    }

    @PostMapping("genreUnBan")
    public void genreUnBan(@AuthenticationPrincipal User user,
                           @RequestBody long genreId) {
        Company company = user.getCompany();
        company.getBannedGenres().removeIf(genre -> genre.getId().equals(genreId));
        companyService.updateCompany(company);

        user.setCompany(company);
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

}
