package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongDto;
import spring.app.model.Genre;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/compilation")
public class UserCompilationRestController {
    private GenreService genreService;
    private SongCompilationService songCompilationService;

    @Autowired
    public UserCompilationRestController(GenreService genreService, SongCompilationService songCompilationService) {
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
    }

    @GetMapping(value = "/all_genre")
    public @ResponseBody
    List<Genre> getAllGenre() {
        return genreService.getAllGenre();
    }

    @GetMapping("/song_compilation")
    public List<SongCompilation> getSongCompilation(String genre) {
        genre = genre.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");

        if (genre.equals("Все подборки")) {
            return songCompilationService.getAllSongCompilations();
        } else {
            Genre genreFromDb = genreService.getByName(genre);
            return songCompilationService.getListSongCompilationsByGenreId(genreFromDb.getId());
        }
    }

    @GetMapping("/songsBySongCompilation")
    public List<SongDto> getSongsBySongCompilation(String compilationName) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationByCompilationName(compilationName);
        List<SongDto> songDtoList = songCompilation.getSong().stream().map(song -> new SongDto(song.getId(), song.getName(), song.getAuthor().getName(), song.getGenre().getName())).collect(Collectors.toList());
        for (SongDto songDto : songDtoList) {
            System.out.println(songDto);
        }
        return songDtoList;
    }
}
