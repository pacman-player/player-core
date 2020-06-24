package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.User;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;

import java.util.*;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorRestController.class);
    private AuthorService authorService;
    private CompanyService companyService;
    private GenreService genreService;

    public AuthorRestController(AuthorService authorService,
                                CompanyService companyService,
                                GenreService genreService) {
        this.authorService = authorService;
        this.companyService = companyService;
        this.genreService = genreService;
    }

    @GetMapping("allAuthors")

    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> list = authorService.getAllAuthors();

        return list;
    }

    @GetMapping("allApprovedAuthors")
    public List<AuthorDto> getAllApprovedAuthors(@AuthenticationPrincipal User user) {
        List<AuthorDto> list = authorService.getAllApprovedAuthors();

        Company company = user.getCompany();
        company = companyService.setBannedEntity(company);
        companyService.checkAndMarkAllBlockedByTheCompany(
                company,
                list);

        return list;
    }

    @GetMapping("approvedAuthorsPage")
    public List<Author> getApprovedAuthorsPage(@AuthenticationPrincipal User user,
                                               @RequestParam(defaultValue = "1") Integer pageNumber,
                                               @RequestParam(defaultValue = "5") Integer pageSize) {
        LOGGER.info("GET request 'approvedAuthorsPage'");
        List<Author> authorsPage = authorService.getApprovedAuthorsPage(pageNumber, pageSize);

        Company company = user.getCompany();
        company = companyService.setBannedEntity(company);
        companyService.checkAndMarkAllBlockedByTheCompany(
                company,
                authorsPage);

        return authorsPage;
    }

    @GetMapping("lastApprovedAuthorsPageNumber")
    public Integer getLastApprovedAuthorsPageNumber(@RequestParam(defaultValue = "5") Integer pageSize) {
        return authorService.getLastApprovedAuthorsPageNumber(pageSize);
    }

    @GetMapping("allAuthorsByName/{name}")
    public List<AuthorDto> searchByNameInAuthors(@PathVariable String name,
                                                 @AuthenticationPrincipal User user) {

        List<AuthorDto> authors = authorService.findAuthorsByNameContaining(name);


        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(usersCompany, authors);
        return authors;
    }

    @PostMapping("authorsBan")
    public void addAuthorInBan(@AuthenticationPrincipal User user,
                               @RequestBody long authorsId) {
        LOGGER.info("POST request 'authorsBan' with authorsId = {}", authorsId);

        Company company = companyService.getById(user.getCompany().getId());
        Author author = authorService.getById(authorsId);
        company.addBannedAuthor(author);

        companyService.update(company);
        user.setCompany(company);
        LOGGER.info("Added to ban Author = {}", author);
    }

    @PostMapping("authorsUnBan")
    public void authorUnBan(@AuthenticationPrincipal User user,
                            @RequestBody long authorsId) {
        LOGGER.info("POST request 'authorsUnBan' with authorsId = {}", authorsId);
        Company company = user.getCompany();
        company.getBannedAuthor().removeIf(author -> author.getId().equals(authorsId));
        companyService.update(company);

        user.setCompany(company);
        LOGGER.info("Removed from ban Author = {}", authorService.getById(authorsId));
    }

    /**
     * список авторов, не относящихся к текущему жанру
     *
     * @param genreID
     * @return
     */
    @PostMapping("/authors_out_of_genre")
    public List<AuthorDto> getAuthorsOutOfGenre(@RequestBody Long genreID) {
        List<AuthorDto> authorsOutGenre = authorService.getAuthorsOutOfGenre(genreID);
        List<AuthorDto> authorsOfGenre = authorService.getAuthorsOfGenre(genreID);
        for (AuthorDto authorOf : authorsOfGenre) {
            authorsOutGenre.removeIf(authorDto -> authorDto.getName().equals(authorOf.getName()));
        }
        Set<AuthorDto> authorDtos = new HashSet<>(authorsOutGenre);
        return new ArrayList<>(authorDtos);
    }

    /**
     * список авторов, относящихся к текущему жанру
     *
     * @param genreID
     * @return
     */
    @PostMapping("/authors_of_genre")
    public List<AuthorDto> getAuthorsOfGenre(@RequestBody Long genreID) {
        List<AuthorDto> authors = authorService.getAuthorsOfGenre(genreID);
        return authors;
    }

    /**
     * список авторов, относящихся к текущему жанру
     *
     * @param updateObject
     * @return
     */
    @PutMapping(value = "/update_authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAuthorsByGenre(@RequestBody Map<Integer, String> updateObject) {
        Genre genre = genreService.getById(Long.valueOf(updateObject.get(-1)));
        String operation = updateObject.get(-2);
        Set<Author> authors = new HashSet<>();
        updateObject.forEach((key, value) -> {
            if (key > 0) {
                authors.add(authorService.getById(Long.parseLong(value)));
            }
        });
        if (operation.equals("add")) {
            genre.addAuthors(authors);
        } else {
            genre.removeAuthors(authors);
        }
        genreService.update(genre);
    }

}
