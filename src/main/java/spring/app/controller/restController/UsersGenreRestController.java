package spring.app.controller.restController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/genre")
public class UsersGenreRestController {

    private GenreService genreService;
    private CompanyService companyService;

    public UsersGenreRestController(GenreService genreService,
                                    CompanyService companyService) {
        this.companyService = companyService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_genres")
    public List<Genre> getAllGenre(@AuthenticationPrincipal User user) {
        List<Genre> allGenre = genreService.getAllGenre();

        Company usersCompany = user.getCompany();

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

