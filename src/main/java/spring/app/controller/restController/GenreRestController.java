package spring.app.controller.restController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.impl.NotificationServiceImpl;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/genre")
public class GenreRestController {
    private final Logger LOGGER = LoggerFactory.getLogger("GenreRestController");
    private GenreService genreService;
    private NotificationServiceImpl notificationService;

    @Autowired
    public GenreRestController(GenreService genreService, NotificationServiceImpl notificationService) {
        this.genreService = genreService;
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/all_genres")
    public List<Genre> getAllGenre() {
        List<Genre> genres = genreService.getAllGenre();
        LOGGER.info("Get request 'all_genres', result {} lines", genres.size());
        return genres;
    }

    @PostMapping(value = "/add_genre")
    public void addGenre(@RequestBody String name) {
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genreService.getByName(name) == null) {
            Genre genre = new Genre();
            genre.setName(name);
            genreService.addGenre(genre);
            LOGGER.info("Post request 'add_genre', genre is = {}", genre);
            try {
                String message = "Was added genre " + name;
                User user = (User) getContext().getAuthentication().getPrincipal();
                notificationService.addNotification(message, user.getId());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @PutMapping(value = "/update_genre")
    public void updateGenre(@RequestBody GenreDto genreDto) {
        Genre genre = genreService.getById(genreDto.getId());
        genre.setName(genreDto.getName());
        genreService.updateGenre(genre);
        LOGGER.info("Put request 'update_genre', genre is = {}", genre);
        try {
            String message = "Genre name " + genre.getName() + " has been changed to " + genreDto.getName();
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.addNotification(message, user.getId());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/delete_genre")
    public void deleteGenre(@RequestBody Long id) {
        Genre genre = genreService.getById(id);
        genreService.deleteGenreById(id);
        LOGGER.info("Delete request 'delete_genre' by id {}", id);
        try {
            String message = "Was delete genre " + genre.getName();
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.addNotification(message, user.getId());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
