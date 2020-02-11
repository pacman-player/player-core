package spring.app.controller.restController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.impl.NotificationServiceImpl;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/genre")
public class GenreRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger("GenreRestController");
    private GenreService genreService;
    private NotificationServiceImpl notificationService;

    @Autowired
    public GenreRestController(GenreService genreService,
                               NotificationServiceImpl notificationService,
                               CompanyService companyService) {
        this.genreService = genreService;
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/all_genres")
    public List<Genre> getAllGenre(@AuthenticationPrincipal User user) {
        List<Genre> genres = genreService.getAllGenre();
        LOGGER.info("GET request '/all_genres'. Result has {} lines", genres.size());
        return genres;
    }

    @PostMapping(value = "/add_genre")
    public void addGenre(@RequestBody String name) {
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genreService.getByName(name) == null) {
            Genre genre = new Genre();
            genre.setName(name);
            genreService.addGenre(genre);
            LOGGER.info("POST request '/add_genre'. Added Genre with name = {}", name);
            try {
                String message = "Was added genre " + name;
                User user = (User) getContext().getAuthentication().getPrincipal();
                notificationService.addNotification(message, user.getId());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @PutMapping(value = "/update_genre")
    public void updateGenre(@RequestBody GenreDto genreDto) {
        Genre genre = genreService.getById(genreDto.getId());
        String genreDtoName = genreDto.getName();
        genre.setName(genreDtoName);
        genreService.updateGenre(genre);
        LOGGER.info("PUT request '/update_genre'. Updated Genre with name = {}", genreDtoName);
        try {
            String message = "Genre name " + genre.getName() + " has been changed to " + genreDtoName;
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.addNotification(message, user.getId());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    @DeleteMapping(value = "/delete_genre")
    public void deleteGenre(@RequestBody Long id) {
        Genre genre = genreService.getById(id);
        genreService.deleteGenreById(id);
        String genreName = genre.getName();
        LOGGER.info("DELETE request '/delete_genre' with id = {}. Deleted Genre = {}", id, genreName);
        try {
            String message = "Was deleted genre " + genreName;
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.addNotification(message, user.getId());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
