package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                              SongCompilationService songCompilation;) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.songService = songService;
        this.companyService = companyService;
        this.songCompilation = songCompilation;
    }

    @GetMapping(value = "/all_genre")
    public @ResponseBody
    List<Genre> getAllGenre() {
        return genreService.getAllGenre();
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

    @PostMapping("authorsBan")
    public void addAuthorInBan(@AuthenticationPrincipal User user,
                               @RequestBody long authorsId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedAuthor(authorService.getById(authorsId));

        companyService.updateCompany(company);
    }

    @PostMapping("songsBan")
    public void addSongInBan(@AuthenticationPrincipal User user,
                             @RequestBody long songId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedSong(songService.getById(songId));

        companyService.updateCompany(company);
    }

    @PostMapping("genreBan")
    public void addGenreInBan(@AuthenticationPrincipal User user,
                              @RequestBody long genreId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedGenre(genreService.getById(genreId));

        companyService.updateCompany(company);
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles() {
        String role = "user";
        User user = (User) getContext().getAuthentication().getPrincipal();
        for (Role roles: user.getRoles()){
            if (roles.getName().equals("ADMIN")){
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
