package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/author/")
public class AdminAuthorRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger("AdminAuthorRestController");
    private final AuthorService authorService;
    private final GenreService genreService;

    public AdminAuthorRestController(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_authors")
    public List<Author> getAllAuthor(){
        List<Author> list = authorService.getAllAuthor();
        LOGGER.info("GET request '/all_authors'. Result has {} lines", list.size());
        return list;
    }

    @GetMapping(value = "/{id}")
    public Author getByIdAuthor(@PathVariable(value = "id") Long authorId){
        Author author = authorService.getById(authorId);
        LOGGER.info("GET request '/{}'. Found Author = {}", authorId, author);
        return author;
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
            LOGGER.info("POST request '/add_author'. Added Author = {}", author);
        }
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody AuthorDto newAuthor){
        Author author = new Author(newAuthor.getId(),newAuthor.getName());
        author.setAuthorGenres(getGenres(newAuthor.getGenres()));
        authorService.updateAuthor(author);
        LOGGER.info("PUT request '/update_author'. Updated Author = {}", author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        authorService.deleteAuthorById(id);
        LOGGER.info("DELETE request '/delete_author' with id = {}", id);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        List<Genre> list = genreService.getAllGenre();
        LOGGER.info("GET request '/all_authors'. Result has {} lines", list.size());
        return list;
    }

    private Set<Genre> getGenres (String nameGenres) {
        Set<Genre> genres = new HashSet<>();
        Genre genre = genreService.getByName(nameGenres);
        genres.add(genre);
        return genres;
    }

    // Returns false if author with requested name already exists else true
    @GetMapping(value = "/is_free")
    public boolean isLoginFree(@RequestParam String name) {
        return authorService.getByName(name) == null;
    }
}
