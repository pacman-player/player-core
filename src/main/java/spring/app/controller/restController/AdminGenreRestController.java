package spring.app.controller.restController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;
import spring.app.model.SongCompilation;
import spring.app.model.User;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.impl.NotificationServiceImpl;

import java.util.List;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/genre")
public class AdminGenreRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminGenreRestController.class);
    private GenreService genreService;
    private SongCompilationService songCompilationService;
    private NotificationServiceImpl notificationService;

    @Autowired
    public AdminGenreRestController(GenreService genreService,
                                    NotificationServiceImpl notificationService,
                                    SongCompilationService songCompilationService) {
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/all_genres")
    public List<GenreDto> getAllGenre(@AuthenticationPrincipal User user) {
        return genreService.getAllGenreDto();

    }

    @PostMapping(value = "/add_genre")
    public void addGenre(@RequestBody String name) {
        LOGGER.info("POST request '/add_genre'");
        name = name.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genreService.getByName(name) == null) {
            Genre genre = new Genre();
            genre.setName(name);
            genreService.save(genre);
            LOGGER.info("Added Genre with name = {}", name);
            try {
                String message = "Was added genre " + name;
                User user = (User) getContext().getAuthentication().getPrincipal();
                notificationService.save(message, user.getId());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @PutMapping(value = "/update_genre")
    public void updateGenre(@RequestBody GenreDto genreDto) {
        LOGGER.info("PUT request '/update_genre'");
        Genre genre = genreService.getById(genreDto.getId());
        String genreDtoName = genreDto.getName();
        genre.setName(genreDtoName);
        genre.setApproved(genreDto.getApproved());
        genreService.update(genre);
        LOGGER.info("Updated Genre with name = {}", genreDtoName);
        try {
            String message = "Genre name " + genre.getName() + " has been changed to " + genreDtoName;
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.save(message, user.getId());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    @PutMapping(value = "/set_default_genre")
    public void setDefaultGenre(@RequestBody long id){
        LOGGER.info("PUT request '/set_default_genre' with id = {}", id);
        Genre genre = genreService.getById(id);
        genreService.deleteDefaultGenre();
        genreService.setDefaultGenre(id);
        LOGGER.info("The default genre is = {}", genre.getName());
        try{
            String message = "The default genre is " + genre.getName();
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.save(message, user.getId());
            LOGGER.info("Sent Notification '{}'", message);
        }
        catch (InterruptedException e){
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    @DeleteMapping(value = "/delete_genre")
    public void deleteGenre(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_genre' with id = {}", id);
        Genre genre = genreService.getById(id);
        genreService.deleteById(id);
        LOGGER.info("Deleted Genre = {}", genre.getName());
        try {
            String message = "Was deleted genre " + genre.getName();
            User user = (User) getContext().getAuthentication().getPrincipal();
            notificationService.save(message, user.getId());
            LOGGER.info("Sent Notification '{}'", message);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    //TODO: remove id param
    @GetMapping(value = "/is_free")
    public boolean isTypeNameFree(@RequestParam("name") String name,
                                  @RequestParam("id") Long id) {
        return !genreService.isExistByName(name);
    }


}
