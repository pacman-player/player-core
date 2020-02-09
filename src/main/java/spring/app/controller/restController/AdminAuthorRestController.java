package spring.app.controller.restController;

import org.springframework.web.bind.annotation.*;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.model.User;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.NotificationService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/v1/admin/author/")
public class AdminAuthorRestController {

    private final AuthorService authorService;
    private final GenreService genreService;

    public AdminAuthorRestController(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_authors")
    public List<Author> getAllAuthor(){
        List<Author> list = authorService.getAllAuthor();
        return list;
    }

    @GetMapping(value = "/{id}")
    public Author getByIdAuthor(@PathVariable(value = "id") Long authorId){
        return authorService.getById(authorId);
    }


    @PostMapping(value = "/add_author")
    public void addAuthor(@RequestBody AuthorDto newAuthor) {
        String name = newAuthor.getName();
        String editName = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        if (authorService.getByName(editName) == null) {
            Author author = new Author();
            author.setName(editName);
            author.setAuthorGenres(getGenres(newAuthor.getGenres()));
            authorService.addAuthor(author);
        }
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody AuthorDto newAuthor){
        Author author = new Author(newAuthor.getId(),newAuthor.getName());
        author.setAuthorGenres(getGenres(newAuthor.getGenres()));
        authorService.updateAuthor(author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        authorService.deleteAuthorById(id);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        List<Genre> list = genreService.getAllGenre();
        return list;
    }

    private Set<Genre> getGenres (String nameGenres) {
        Set<Genre> genres = new HashSet<>();
        Genre genre = genreService.getByName(nameGenres);
        genres.add(genre);
        return genres;
    }

}
