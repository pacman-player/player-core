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
    private final UserService userService;
    private final GenreService genreService;
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
        this.userService = userService;
        this.genreService = genreService;
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
