package core.app.controller.restController;

import core.app.model.Author;
import core.app.model.Company;
import core.app.model.User;
import core.app.service.abstraction.AuthorService;
import core.app.service.abstraction.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public List<Author> getAllAuthor() {
        LOGGER.info("GET request 'allAuthors'");
        List<Author> list = authorService.getAllAuthor();
        LOGGER.info("Result has {} lines", list.size());
        return list;
    }

    @GetMapping("allAuthorsByName/{name}")
    public List<Author> searchByNameInAuthors(@PathVariable String name,
                                              @AuthenticationPrincipal User user) {
        LOGGER.info("GET request 'allAuthorsByName/{}'", name);
        List<Author> authors = authorService.findAuthorsByNameContaining(name);

        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(usersCompany, authors);
        LOGGER.info("Result has {} lines", authors.size());
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
