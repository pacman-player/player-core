package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AddressDto;
import spring.app.dto.CompanyDto;
import spring.app.dto.UserDto;
import spring.app.model.*;
import spring.app.service.EmailPasswordGeneration;
import spring.app.service.EmailSender;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    //эти два поля для дальнейшего раширенияфункционала,если непонадобятся-удалить!!!
    private final RoleService roleService;
    private final UserService userService;

    private final GenreService genreService;
    private final CompanyService companyService;
    private final SongCompilationService songCompilation;
    private final AddressService addressService;

    private String PASSWORD = "";

    @Autowired
    public EmailSender emailSender;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(RoleService roleService,
                              UserService userService,
                              CompanyService companyService,
                              GenreService genreService,
                              AuthorService authorService,
                              SongService songService,
                              SongCompilationService songCompilation,
                              AddressService addressService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.companyService = companyService;
        this.songCompilation = songCompilation;
        this.addressService = addressService;
    }

    @PostMapping(value = "/song_compilation")
    public @ResponseBody
    List<SongCompilation> getSongCompilation(@RequestBody String genre) {
        LOGGER.info("POST request '/song_compilation' with genre = {}", genre);
        genre = genre.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genre.equals("Все подборки")) {
            LOGGER.info("Returning all compilations");
            return songCompilation.getAll();
        } else {
            Genre genres = genreService.getByName(genre);
            List<SongCompilation> compilations = songCompilation.getListSongCompilationsByGenreId(genres.getId());
            LOGGER.info("Found {} compilation(s)", compilations.size());
            return compilations;
        }
    }

    @GetMapping(value = "/get_user")
    public UserDto getUserData(){
        User user = (User) getContext().getAuthentication().getPrincipal();
        return (userService.getUserDtoById(user.getId()));

    }

    @PostMapping(value = "/get_encrypted_pass")
    public ResponseEntity<Boolean> getEncPass(@RequestParam Map<String, String> json) {
        LOGGER.info("POST request '/get_encrypted_pass'");
        if (json.get("prevPass").isEmpty()) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(passwordEncoder.matches(json.get("nextPass"), json.get("prevPass")));
    }

    @PutMapping(value = "/edit_data")
    public ResponseEntity<User> editUserData(@RequestBody User newUser){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        LOGGER.info("PUT request '/edit_data' from User = {}", user);
        if(!newUser.getLogin().equals(user.getLogin())) {
            if (userService.getUserByLogin(newUser.getLogin()) == null) {
                user.setLogin(newUser.getLogin());
                LOGGER.info("User has changed his login successfully!");
            }else{
                LOGGER.info("Bad request: User and Principal logins don't match");
                return ResponseEntity.badRequest().body(user);
            }
        }
        if(!newUser.getEmail().equals(user.getEmail())){
            if(userService.getUserByEmail(newUser.getEmail()) == null){
                user.setEmail(newUser.getEmail());
                LOGGER.info("User has changed his email successfully!");
            }else{
                LOGGER.info("Bad request: User and Principal emails don't match");
                return ResponseEntity.badRequest().body(user);
            }
        }
        userService.update(user);
        LOGGER.info("Updated user data for User = {}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/edit_pass")
    public void editUserPass(@RequestBody String newPassword){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        LOGGER.info("PUT request '/edit_pass' from User = {}", user);
        newPassword = newPassword.substring(1, newPassword.length()-1);
        newPassword = newPassword.replaceAll("##@##"  , "\"");
        newPassword = newPassword.replaceAll("##@@##"  ,"\\\\");

        user.setPassword(newPassword);
        userService.update(user);
        LOGGER.info("Password has been changed successfully!");
    }

    @PostMapping(value = "/show_admin")//запрос на показ вкладки админ на странице user
    public String getUserRoles() {
        User user = (User) getContext().getAuthentication().getPrincipal();
        LOGGER.info("POST request '/show_admin' from User = {}", user);
        String role = "user";
        for (Role roles : user.getRoles()) {
            if (roles.getName().equals("ADMIN")) {
                role = "admin";
                LOGGER.info("User has ADMIN role");
                return role;
            }
        }
        LOGGER.info("User doesn't have ADMIN role");
        return role;
    }

    @GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompanyDto> getUserCompany() {
        User user = (User) getContext().getAuthentication().getPrincipal();
        User userLazy = userService.getUserByLoginWithRegStepsCompany(user.getLogin());
        long id = userLazy.getCompany().getId();
        return ResponseEntity.ok(companyService.getCompanyDtoById(id));
    }

    // Кандидат на удаление
//    @GetMapping(value = "/company/address", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Company> getUserCompanyAddress() {
//        User user = (User) getContext().getAuthentication().getPrincipal();
//        LOGGER.info("GET request '/company/address' from User = {}", user.getLogin());
//        User lazyUser = userService.getUserByLoginWithRegStepsCompany(user.getLogin());
//        Company company = companyService.getByIdWithAddress(lazyUser.getCompany().getId());
////        Address address = addressService.getById(company.getAddress().getId()); // строчка не нужна, т.к. в js address получаем  из объекта company
//        return ResponseEntity.ok(company);
//    }

    @PutMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateCompany(@RequestBody CompanyDto company) {
        User user = (User) getContext().getAuthentication().getPrincipal();
        LOGGER.info("PUT request '/company' from User = {}", user);
        long id = user.getCompany().getId();
        Company companyForUpdate = companyService.getById(id);
        companyForUpdate.setName(company.getName());
        companyForUpdate.setStartTime(LocalTime.parse(company.getStartTime()));
        companyForUpdate.setCloseTime(LocalTime.parse(company.getCloseTime()));
        companyForUpdate.setTariff(company.getTariff());
        companyService.update(companyForUpdate);
        LOGGER.info("Updated Company = {}", companyForUpdate);
    }

    @PutMapping(value = "/company/address", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateAddress(@RequestBody AddressDto addressDto) {
        User user = (User) getContext().getAuthentication().getPrincipal();
        LOGGER.info("PUT request '/company/address' from User = {}", user);
        long id = user.getCompany().getId();
        Company companyForUpdate = companyService.getById(id);
        Address addressForUpdate = companyForUpdate.getAddress();

        LOGGER.info("Updating address for Company named = {}", companyForUpdate.getName());
        if (addressForUpdate == null) {
            LOGGER.debug("Creating new address...");
            addressService.update(new Address(
                    addressDto.getCountry(),
                    addressDto.getCity(),
                    addressDto.getStreet(),
                    addressDto.getHouse(),
                    addressDto.getLatitude(),
                    addressDto.getLongitude()
            ));
            LOGGER.debug("Success!");
        } else {
            LOGGER.debug("Updating existing new address...");
            addressForUpdate.setCountry(addressDto.getCountry());
            addressForUpdate.setCity(addressDto.getCity());
            addressForUpdate.setStreet(addressDto.getStreet());
            addressForUpdate.setHouse(addressDto.getHouse());
            addressForUpdate.setLatitude(addressDto.getLatitude());
            addressForUpdate.setLongitude(addressDto.getLongitude());

            companyForUpdate.setAddress(addressForUpdate);
            companyService.update(companyForUpdate);

            LOGGER.debug("Success!");
            return;
        }

        companyForUpdate.setAddress(addressService.getById(addressService.getLastId()));
        companyService.update(companyForUpdate);
        LOGGER.info("Successfully updated address for the Company");
    }

    @PutMapping(value = "/code_check")
    public ResponseEntity<String> codeCheck(@RequestBody String code){
        LOGGER.info("PUT request '/code_check'");
        code = code.substring(1, code.length()-1);
        if(code.equals(PASSWORD)){
            LOGGER.info("Success!");
            return ResponseEntity.ok("Пароль совпадает");
        }
        LOGGER.info("Unsuccessful!");
        return ResponseEntity.badRequest().body("Пароль не совпадает");
    }

    @PutMapping(value = "/send_mail")
    public void sendMail(){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        LOGGER.info("PUT request '/send_mail' for User = {}", user);
        EmailPasswordGeneration emailPasswordGeneration = new EmailPasswordGeneration();
        PASSWORD = emailPasswordGeneration.generate();
        System.out.println(PASSWORD);
        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Для смены пароля введите код подтверждения: " + PASSWORD + "\n " +
                        "Если вы не запрашивали смену пароля, свяжитесь со службой технической поддержки.",
                user.getLogin()
        );

        if(user.getEmail() != null
                && !user.getEmail().equals("user@gmail.com")
                && !user.getEmail().equals("admin@gmail.com")) {
            emailSender.send(user.getEmail(), "Смена пароля", message);
        }

    }
}
