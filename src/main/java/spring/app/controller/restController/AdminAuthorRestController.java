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
        List<AuthorDto> authorDtoList = authorService.getAllAuthors();

        return authorDtoList;
    }

    @GetMapping(value = "/{id}")
    public Author getByIdAuthor(@PathVariable(value = "id") Long authorId) {
        Author author = authorService.getById(authorId);
        return author;
    }

    @PostMapping(value = "/add_author")
    public void addAuthor(@RequestBody AuthorDto newAuthor) {
        LOGGER.info("POST request '/add_author' with new Author = {}", newAuthor.getName());
        String editName = (newAuthor.getName()).replaceAll("[^A-Za-zА-Яа-я0-9-& ]", "");
        if (authorService.getByName(editName) == null) {
            Author author = new Author();
            author.setName(editName);
            author.setAuthorGenres(getGenres(newAuthor.getGenres()));
            author.setApproved(newAuthor.getApproved());
            authorService.save(author);
            LOGGER.info("Added new Author = {}", author);
        } else {
            LOGGER.info("New Author was not added!");
        }
    }

    @PutMapping(value = "/update_author")
    public void updateAuthor(@RequestBody AuthorDto newAuthor) {
        LOGGER.info("PUT request '/update_author' to update author = {}", newAuthor.getName());
        Author author = new Author(
                newAuthor.getId(),
                newAuthor.getName(),
                getGenres(newAuthor.getGenres()),
                newAuthor.getApproved());
        authorService.update(author);
        LOGGER.info("Updated Author = {}", author);
    }

    @DeleteMapping(value = "/delete_author")
    public void deleteAuthor(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_author' with id = {}", id);
        authorService.deleteById(id);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        List<Genre> list = genreService.getAll();
        return list;
    }

    private Set<Genre> getGenres(String[] genresArr) {
        //С использованием стримов проходим по каждому жанру в массиве,
        //находим его в БД и добавляем данный объект Genre в наш Set<Genre>
        return Arrays.stream(genresArr).map(genreService::getByName).collect(Collectors.toSet());
    }

    /**
     * В метод передается значение поля 'name' с формы редактирования и 'id' редактируемого элемента. Метод должен вернуть false только в случае, когда имя совпадает с именем другого исполнителя (т.е. предотвратить ConstraintViolationException, т.к. поле name - unique)
     */
    @GetMapping(value = "/is_free")
    public boolean isLoginFree(@RequestParam String name,
                               @RequestParam(name = "id", required = false) Long id) {
        Author author = authorService.getByName(name);
        if (id == null) {
            return (author == null);
        } else return (author == null || author == authorService.getById(id));
    }
}

