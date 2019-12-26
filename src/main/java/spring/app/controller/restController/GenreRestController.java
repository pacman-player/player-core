package spring.app.controller.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/genre")
public class GenreRestController {

    private GenreService genreService;
    private SongCompilationService songCompilationService;

    @Autowired
    public GenreRestController(GenreService genreService, SongCompilationService songCompilationService) {
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
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
        }

    }

    @PutMapping(value = "/update_genre")
    public void updateGenre(@RequestBody GenreDto genreDto) {
        Genre genre = genreService.getById(genreDto.getId());
        genre.setName(genreDto.getName());
        genreService.updateGenre(genre);
    }

    @DeleteMapping(value = "/delete_genre")
    public void deleteGenre(@RequestBody Long id) {
        songCompilationService.deleteValByGenreId(id);
        genreService.deleteGenreById(id);
    }


}
