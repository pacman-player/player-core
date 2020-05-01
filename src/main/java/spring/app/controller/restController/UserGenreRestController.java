package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;

import java.util.List;

@RestController
@RequestMapping("api/user/genre")
public class UserGenreRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserGenreRestController.class);
    private GenreService genreService;
    private CompanyService companyService;

    @Autowired
    public UserGenreRestController(GenreService genreService,
                                   CompanyService companyService) {
        this.genreService = genreService;
        this.companyService = companyService;
    }

    @GetMapping(value = "/get/all-genre")
    public List<Genre> getAllGenre(@AuthenticationPrincipal User user) {
        LOGGER.info("GET request '/get/all-genre' for User = {}", user);
        List<Genre> allGenre = genreService.getAllApprovedGenre();
        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(
                usersCompany,
                allGenre);
        LOGGER.info("Result has {} lines", allGenre.size());
        return allGenre;
    }

    @PostMapping("/genreBan")
    public void addGenreInBan(@AuthenticationPrincipal User user,
                              @RequestBody long genreId) {
        LOGGER.info("POST request '/genreBan' for User = {} with genreId = {}", user, genreId);
        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedGenre(genreService.getById(genreId));

        companyService.update(company);
        user.setCompany(company);
    }

    @PostMapping("/genreUnBan")
    public void genreUnBan(@AuthenticationPrincipal User user,
                           @RequestBody long genreId) {
        LOGGER.info("POST request '/genreUnBan' for User = {} with genreId = {}", user, genreId);
        Company company = user.getCompany();
        company.getBannedGenres().removeIf(genre -> genre.getId().equals(genreId));
        companyService.update(company);

        user.setCompany(company);
    }
}
