package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Author;
import spring.app.model.Company;
import spring.app.model.User;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.CompanyService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/author")
public class AuthorRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorRestController.class);
    private AuthorService authorService;
    private CompanyService companyService;

    public AuthorRestController(AuthorService authorService,
                                CompanyService companyService) {
        this.authorService = authorService;
        this.companyService = companyService;
    }

    @GetMapping("allAuthors")
    public List<Author> getAllAuthors() {
        List<Author> list = authorService.getAllAuthors();
        return list;
    }

    @GetMapping("allApprovedAuthors")
    public List<Author> getAllApprovedAuthors(@AuthenticationPrincipal User user) {
        List<Author> list = authorService.getAllApprovedAuthors();

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
    public List<Author> searchByNameInAuthors(@PathVariable String name,
                                              @AuthenticationPrincipal User user) {
        List<Author> authors = authorService.findAuthorsByNameContaining(name);

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

        companyService.updateCompany(company);
        user.setCompany(company);
        LOGGER.info("Added to ban Author = {}", author);
    }

    @PostMapping("authorsUnBan")
    public void authorUnBan(@AuthenticationPrincipal User user,
                            @RequestBody long authorsId) {
        LOGGER.info("POST request 'authorsUnBan' with authorsId = {}", authorsId);
        Company company = user.getCompany();
        company.getBannedAuthor().removeIf(author -> author.getId().equals(authorsId));
        companyService.updateCompany(company);

        user.setCompany(company);
        LOGGER.info("Removed from ban Author = {}", authorService.getById(authorsId));
    }

}
