package spring.app.controller.restController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Author;
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

        companyService.checkAndMarkAllBlockedByTheCompany(
                user.getCompany(),
                authors);
        return authors;
    }
}
