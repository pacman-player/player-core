package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;

import java.util.List;

@RestController
@RequestMapping("api/user/genre")
public class UserGenreRestController {
    private GenreService genreService;

    @Autowired
    public UserGenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/get/all-genre")
    public List<Genre> getAllGenre() {
        return genreService.getAllGenre();
    }
}
