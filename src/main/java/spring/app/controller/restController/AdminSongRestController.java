package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin/song/")
public class AdminSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSongRestController.class);
    private final SongService songService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public AdminSongRestController(SongService songService, AuthorService authorService, GenreService genreService) {
        this.songService = songService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    /*
     * Необходимо переработать "в глубь" неверно, что мы сперва тянем сущности из БД, а затем парсим их в DTO
     * Необходимо сделать так, чтобы DAO возвращал список DTO
     */
    @GetMapping(value = "/all_songs")
    public List<SongDto> getAllSongs() {
        LOGGER.info("GET request '/all_songs'");
        List<SongDto> list = songService.getAllSongs()
                .stream()
                .map(SongDto::new)
                .collect(Collectors.toList());
        LOGGER.info("Result has {} lines", list.size());
        return list;
    }

    @DeleteMapping(value = "/delete_song/{id}")
    public void deleteSong(@PathVariable("id") Long id) {
        LOGGER.info("DELETE request '/delete_song/{}'", id);
        songService.deleteSongById(id);
    }

    /*
    Чтобы добавить новую песню сначала проверяю автора на наличие в бд.
     */
    @PostMapping(value = "/add_song")
    public void addSong(@RequestBody SongDto songDto) {
        LOGGER.info("POST request '/add_song'");
        Song song = new Song(songDto.getName());
        Author author = authorService.getByName(songDto.getAuthorName());
        if (author != null) {
            song.setAuthor(author);
        } else {
            authorService.addAuthor(new Author(songDto.getAuthorName()));
            song.setAuthor(authorService.getByName(songDto.getAuthorName()));
        }
        Genre genre = genreService.getByName(songDto.getGenreName());
        if (genre != null) {
            song.setGenre(genre);
        }
        songService.addSong(song);
        LOGGER.info("Added Song = {}", song);
    }

    /*
    Чтобы изменить песню сначала достаю исходную песню со всеми полями.
     */
    @PutMapping(value = "/update_song")
    public void updateSong(@RequestBody SongDto songDto) {
        LOGGER.info("PUT request '/update_song'");
        Song oldSong = songService.getSongById(songDto.getId());
        LOGGER.info("Changing Song = {}", oldSong);
        Author author = oldSong.getAuthor();
        Genre genre = genreService.getByName(songDto.getGenreName());
        Song song = new Song(songDto.getId(), songDto.getName());
        Boolean isApproved = songDto.getApproved();
        song.setApproved(isApproved);
        song.setAuthor(author);
        song.setGenre(genre);
        songService.updateSong(song);
        LOGGER.info("Updated Song as = {}", song);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<SongDto> getSongById(@PathVariable(value = "id") Long id) {
        LOGGER.info("GET request '/{}'", id);
        SongDto songDto = new SongDto(songService.getSongById(id));
        LOGGER.info("Found Sing = {}", songDto);
        return ResponseEntity.ok(songDto);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        LOGGER.info("GET request '/all_genre'");
        List<Genre> list = genreService.getAllGenre();
        LOGGER.info("Result has {} lines", list.size());
        return list;
    }
}
