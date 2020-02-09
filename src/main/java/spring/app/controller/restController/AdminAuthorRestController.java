package spring.app.controller.restController;

import org.springframework.web.bind.annotation.*;
import spring.app.model.Author;
import spring.app.service.abstraction.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/author/")
public class AdminAuthorRestController {

    private final AuthorService authorService;

    public AdminAuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/all_authors")
    public List<Author> getAllAuthor(){
        List<Author> list = authorService.getAllAuthor();
        return list;
    }

    @PostMapping(value = "/add_author")
    public void addAuthor(@RequestBody String name){
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        if (authorService.getByName(name) == null) {
            Author author = new Author();
            author.setName(name);
            authorService.addAuthor(author);
        }
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody Author newAuthor){
        Author author = authorService.getById(newAuthor.getId());
        author.setName(newAuthor.getName());
        authorService.updateAuthor(author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        authorService.deleteAuthorById(id);
    }

}
