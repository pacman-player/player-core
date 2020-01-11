package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Author;
import spring.app.service.abstraction.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/author/")
public class AdminAuthorRestController {
    private final Logger LOGGER = LoggerFactory.getLogger("AdminAuthorRestController");
    private final AuthorService authorService;

    public AdminAuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/all_authors")
    public List<Author> getAllAuthor(){
        List<Author> list = authorService.getAllAuthor();
        LOGGER.info("Get request 'all_authors', result {} lines", list.size());
        return list;
    }

    @PostMapping(value = "/add_author")
    public void addAuthor(@RequestBody String name){
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        if (authorService.getByName(name) == null) {
            Author author = new Author();
            author.setName(name);
            authorService.addAuthor(author);
            LOGGER.info("Post request 'add_author', author is = {}", author);
        }
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody Author newAuthor){
        Author author = authorService.getById(newAuthor.getId());
        author.setName(newAuthor.getName());
        authorService.updateAuthor(author);
        LOGGER.info("Put request 'update_author', author is = {}", author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        authorService.deleteAuthorById(id);
        LOGGER.info("Delete request 'delete_author' by id = {}", id);
    }
}
