package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongDto;
import spring.app.model.Genre;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/song-compilation")
public class UserCompilationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserCompilationRestController.class);
    private GenreService genreService;
    private SongCompilationService songCompilationService;

    @Autowired
    public UserCompilationRestController(GenreService genreService, SongCompilationService songCompilationService) {
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
    }

    @PostMapping(value = "/get/all-song-compilation")
    public List<SongCompilation> getSongCompilation(@RequestBody String genre) {
        LOGGER.info("GET request '/get/all-song-compilation' with genre = {}", genre);
        genre = genre.replaceAll("[^A-Za-zА-Яа-я0-9-& ]", "");

        if (genre.equals("Все подборки")) {
            LOGGER.info("Returning all compilations");
            return songCompilationService.getAllSongCompilations();
        } else {
            LOGGER.info("Returning compilations by provided genre...");
            Genre genres = genreService.getByName(genre);
            List<SongCompilation> compilations = songCompilationService.getListSongCompilationsByGenreId(genres.getId());
            LOGGER.info("Found {} compilation(s)", compilations.size());
            return compilations;
        }
    }

    @GetMapping(value = "/get/song-compilation/{id}")
    public SongCompilation getSongCompilationById(@PathVariable("id") Long id) {
        LOGGER.info("GET request '/get/all-song-compilation/{}'", id);
        SongCompilation songCompilationById = songCompilationService.getSongCompilationById(id);
        LOGGER.info("Found compilation = {}", songCompilationById.getName());
        return songCompilationById;
    }

    /*
     * Необходимо переработать "в глубь" неверно, что мы сперва тянем сущности из БД, а затем парсим их в DTO
     * Необходимо сделать так, чтобы DAO возвращал список DTO
     */
    @GetMapping("/songsBySongCompilation")
    public List<SongDto> getSongsBySongCompilation(String compilationName) {
        LOGGER.info("GET request '/songsBySongCompilation' with compilationName = {}", compilationName);
        SongCompilation songCompilation = songCompilationService.getSongCompilationByCompilationName(compilationName);
        List<SongDto> songDtoList = songCompilation.getSong().stream()
                .map(SongDto::new)
                .collect(Collectors.toList());
        LOGGER.info("Found {} songs", songDtoList.size());
        return songDtoList;
    }
}