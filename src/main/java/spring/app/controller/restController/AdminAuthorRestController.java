package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/author")
public class AdminAuthorRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminAuthorRestController.class);
    private final AuthorService authorService;
    private final GenreService genreService;

    public AdminAuthorRestController(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_authors")
    public List<AuthorDto> getAllAuthor(){
        LOGGER.info("GET request '/all_authors'");
        List<Author> authorList = authorService.getAllAuthor();
        //Проходимся по листу авторов и делаем AuthorDto из каждого Author
        List<AuthorDto> authorDtoList = authorList.stream().map(AuthorDto::new).collect(Collectors.toList());
        LOGGER.info("Result has {} lines", authorDtoList.size());
        return authorDtoList;
    }

    @GetMapping(value = "/{id}")
    public Author getByIdAuthor(@PathVariable(value = "id") Long authorId){
        LOGGER.info("GET request '/{}'", authorId);
        Author author = authorService.getById(authorId);
        LOGGER.info("Found Author = {}", author);
        return author;
    }

    @PostMapping(value = "/add_author")
    public void addAuthor(@RequestBody AuthorDto newAuthor) {
        LOGGER.info("POST request '/add_author' with new Author = {}", newAuthor.getName());
        String editName = (newAuthor.getName()).replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        if (authorService.getByName(editName) == null) {
            Author author = new Author();
            author.setName(editName);
            author.setAuthorGenres(getGenres(newAuthor.getGenres()));
            authorService.addAuthor(author);
            LOGGER.info("Added new Author = {}", author);
        } else {
            LOGGER.info("New Author was not added!");
        }
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody AuthorDto newAuthor) {
        LOGGER.info("PUT request '/update_author' to update author = {}", newAuthor.getName());
        Author author = new Author(newAuthor.getId(),newAuthor.getName());
        author.setAuthorGenres(getGenres(newAuthor.getGenres()));
        authorService.updateAuthor(author);
        LOGGER.info("Updated Author = {}", author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id){
        LOGGER.info("DELETE request '/delete_author' with id = {}", id);
        authorService.deleteAuthorById(id);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        LOGGER.debug("GET request '/all_genres' for 'select' tag");
        List<Genre> list = genreService.getAllGenre();
        LOGGER.debug("Result has {} lines", list.size());
        return list;
    }

    private Set<Genre> getGenres(String[] genresArr) {
        //С использованием стримов проходим по каждому жанру в массиве,
        //находим его в БД и добавляем данный объект Genre в наш Set<Genre>
        return Arrays.stream(genresArr).map(genreService::getByName).collect(Collectors.toSet());
    }

    // Returns false if author with requested name already exists else true
    @GetMapping(value = "/is_free")
    public boolean isLoginFree(@RequestParam String name) {
        LOGGER.info("GET request '/is_free' for name = {}", name);
        boolean isLoginFree = (authorService.getByName(name) == null);
        LOGGER.info("Returned = {}", isLoginFree);
        return isLoginFree;
    }
}
