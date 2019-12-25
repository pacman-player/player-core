package spring.app.controller.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.impl.NotificationServiceImpl;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/genre")
public class GenreRestController {

    private GenreService genreService;
    private NotificationServiceImpl notificationService;
    //private User user = (User) getContext().getAuthentication().getPrincipal();

    @Autowired
    public GenreRestController(GenreService genreService, NotificationServiceImpl notificationService) {
        this.genreService = genreService;
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/all_genres")
    public List<Genre> getAllGenre() {
        return genreService.getAllGenre();
    }

    @PostMapping(value = "/add_genre")
    public void addGenre(@RequestBody String name) {
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genreService.getByName(name) == null) {

            Genre genre = new Genre();
            genre.setName(name);
            genreService.addGenre(genre);

            Notification notification = new Notification();
            notification.setUser((User) getContext().getAuthentication().getPrincipal());
            notification.setMessage("Was added genre " + name);
            notificationService.addNotification(notification);
        }

    }

    @PutMapping(value = "/update_genre")
    public void updateGenre(@RequestBody GenreDto genreDto) {
        Genre genre = genreService.getById(genreDto.getId());
        genre.setName(genreDto.getName());
        genreService.updateGenre(genre);

        Notification notification = new Notification();
        notification.setUser((User) getContext().getAuthentication().getPrincipal());
        notification.setMessage("Genre name " + genre.getName() + " has been changed to " + genreDto.getName());
        notificationService.addNotification(notification);
    }

    @DeleteMapping(value = "/delete_genre")
    public void deleteGenre(@RequestBody Long id) {
        Genre genre = genreService.getById(id);
        genreService.deleteGenreById(id);

        Notification notification = new Notification();
        notification.setUser((User) getContext().getAuthentication().getPrincipal());
        notification.setMessage("Was delete genre " + genre.getName());
        notificationService.addNotification(notification);
    }


}
