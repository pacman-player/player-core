package spring.app.controller.restController;

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
        List<Genre> allGenre = genreService.getAllGenre();

        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(
                usersCompany,
                allGenre);

        return allGenre;
    }

    @PostMapping("/genreBan")
    public void addGenreInBan(@AuthenticationPrincipal User user,
                              @RequestBody long genreId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedGenre(genreService.getById(genreId));

        companyService.updateCompany(company);
        user.setCompany(company);
    }

    @PostMapping("/genreUnBan")
    public void genreUnBan(@AuthenticationPrincipal User user,
                           @RequestBody long genreId) {
        Company company = user.getCompany();
        company.getBannedGenres().removeIf(genre -> genre.getId().equals(genreId));
        companyService.updateCompany(company);

        user.setCompany(company);
    }
}
