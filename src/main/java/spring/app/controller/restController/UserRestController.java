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

    private final CompanyService companyService;
    private final GenreService genreService;
    private final SongCompilationService songCompilationService;
    private final SongService songService;

    @Autowired
    public UserRestController(RoleService roleService, UserService userService, CompanyService companyService, GenreService genreService, SongCompilationService songCompilationService, SongService songService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
        this.songService = songService;
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
            return songCompilationService.getAllSongCompilations();
        } else {
            Genre genres = genreService.getByName(genre);
            List<SongCompilation> list = songCompilationService.getListSongCompilationsByGenreId(genres.getId());
            return songCompilationService.getListSongCompilationsByGenreId(genres.getId());
        }
    }

    @GetMapping(value = "/add_song_compilation_to_morning_playlist/{id}")
    public void addSongCompilationToMorningPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToMorningPlaylist(id);
    }

    @GetMapping(value = "/add_song_compilation_to_midday_playlist/{id}")
    public void addSongCompilationToMiddayPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToMiddayPlaylist(id);
    }

    @GetMapping(value = "/add_song_compilation_to_evening_playlist/{id}")
    public void addSongCompilationToEveningPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToEveningPlaylist(id);
    }

    @GetMapping(value = "/get_all_compilations_in_morning_playlist")
    public List<SongCompilation> getAllCompilationsInMorningPlaylist() {
        return songCompilationService.getAllCompilationsInMorningPlaylist();
    }

    @GetMapping(value = "/get_all_compilations_in_midday_playlist")
    public List<SongCompilation> getAllCompilationsInMiddayPlaylist() {
        return songCompilationService.getAllCompilationsInMiddayPlaylist();
    }

    @GetMapping(value = "/get_all_compilations_in_evening_playlist")
    public List<SongCompilation> getAllCompilationsInEveningPlaylist() {
        return songCompilationService.getAllCompilationsInEveningPlaylist();
    }

    @GetMapping(value = "/all_song_in_song_compilation/{id}")
    public List<Song> getAllSongInSongCompilation(@PathVariable("id") Long id) {
        return songService.getAllSongInSongCompilation(id);
    }

    @GetMapping(value = "/song_compilation/{id}")
    public SongCompilation getSongCompilationById(@PathVariable("id") Long id) {
        return songCompilationService.getSongCompilationById(id);
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
