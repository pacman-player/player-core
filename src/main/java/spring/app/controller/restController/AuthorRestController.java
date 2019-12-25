package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.service.abstraction.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/author")
public class AuthorRestController {

    private AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping(value = "/all_author")
    public List<Author> getAllAuthor(){
        return authorService.getAllAuthor();
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody AuthorDto authorDto){
        Author author = authorService.getById(authorDto.getId());
        author.setName(authorDto.getName());
        authorService.updateAuthor(author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        authorService.deleteAuthorById(id);
    }

}
