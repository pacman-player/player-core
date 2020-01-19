package spring.app.controller.restController;

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

    private AuthorService authorService;
    private CompanyService companyService;

    public AuthorRestController(AuthorService authorService,
                                CompanyService companyService) {
        this.authorService = authorService;
        this.companyService = companyService;
    }

    @GetMapping("allAuthors")
    public List<Author> getAllAuthor() {
        return authorService.getAllAuthor();
    }

    @GetMapping("allAuthorsByName/{name}")
    public List<Author> searchByNameInAuthors(@PathVariable String name,
                                              @AuthenticationPrincipal User user) {
        List<Author> authors = authorService.findAuthorsByNameContaining(name);

        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(
                usersCompany,
                authors);
        return authors;
    }

    @PostMapping("authorsBan")
    public void addAuthorInBan(@AuthenticationPrincipal User user,
                               @RequestBody long authorsId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedAuthor(authorService.getById(authorsId));

        companyService.updateCompany(company);
        user.setCompany(company);
    }

    @PostMapping("authorsUnBan")
    public void authorUnBan(@AuthenticationPrincipal User user,
                            @RequestBody long authorsId) {
        Company company = user.getCompany();
        company.getBannedAuthor().removeIf(author -> author.getId().equals(authorsId));
        companyService.updateCompany(company);

        user.setCompany(company);
    }

}
