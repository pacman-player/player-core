package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.app.dto.CompanyDto;
import spring.app.dto.UserDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final GenreService genreService;
    private final OrgTypeService orgTypeService;
    private final SongCompilationService songCompilationService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, CompanyService companyService,
                               GenreService genreService, OrgTypeService orgTypeService, SongCompilationService songCompilationService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.genreService = genreService;
        this.orgTypeService = orgTypeService;
        this.songCompilationService = songCompilationService;
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

    @GetMapping(value = "/all_establishments")
    public @ResponseBody
    List<OrgType> getAllEstablishments() {
        List<OrgType> list = orgTypeService.getAllOrgType();
        return list;
    }

    @GetMapping(value = "/all_compilations")
    public @ResponseBody
    List<SongCompilation> getAllCompilations() {
        List<SongCompilation> compilations = songCompilationService.getAllSongCompilations();
        return compilations;
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

    @PostMapping(value = "/add_establishment")
    public void addEstablishment(@RequestBody OrgType orgType) {
        orgTypeService.addOrgType(orgType);
    }

    @PutMapping(value = "/update_establishment")
    public void updateEstablishment(@RequestBody OrgType orgType) {
        orgTypeService.updateOrgType(orgType);
    }

    @DeleteMapping(value = "/delete_establishment")
    public void deleteEstablishment(@RequestBody Long id) {
        orgTypeService.deleteOrgTypeById(id);
    }

    @PutMapping(value = "/update_compilation_pic")
    public void updateCompilationPic(@RequestBody MultipartFile file) {
        //TODO
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(1L);
        try {
            Blob blob = new SerialBlob(file.getBytes());
            songCompilation.setCompilationPic(blob);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
