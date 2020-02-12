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
    private final static Logger LOGGER = LoggerFactory.getLogger("UserCompilationRestController");
    private GenreService genreService;
    private SongCompilationService songCompilationService;

    @Autowired
    public UserCompilationRestController(GenreService genreService, SongCompilationService songCompilationService) {
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
    }

    @PostMapping(value = "/get/all-song-compilation")
    public List<SongCompilation> getSongCompilation(@RequestBody String genre) {
        genre = genre.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        LOGGER.info("GET request '/get/all-song-compilation' with genre = {}", genre);
        if (genre.equals("Все подборки")) {
            return songCompilationService.getAllSongCompilations();
        } else {
            Genre genres = genreService.getByName(genre);
            return songCompilationService.getListSongCompilationsByGenreId(genres.getId());
        }
    }

    @GetMapping(value = "/get/song-compilation/{id}")
    public SongCompilation getSongCompilationById(@PathVariable("id") Long id) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(id);
        LOGGER.info("GET request '/get/all-song-compilation/{}'. Found SongCompilation named = {}",
                id,
                songCompilation.getName());
        return songCompilation;
    }

    @GetMapping("/songsBySongCompilation")
    public List<SongDto> getSongsBySongCompilation(String compilationName) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationByCompilationName(compilationName);
        List<SongDto> songDtoList = songCompilation.getSong().stream()
                .map(song -> new SongDto(
                        song.getId(),
                        song.getName(),
                        song.getAuthor().getName(),
                        song.getGenre().getName())
                )
                .collect(Collectors.toList());
        for (SongDto songDto : songDtoList) {
            System.out.println(songDto);
        }
        LOGGER.info("GET request '/songsBySongCompilation' with compilationName = {}. Found {} songs",
                compilationName,
                songDtoList.size());
        return songDtoList;
    }
}