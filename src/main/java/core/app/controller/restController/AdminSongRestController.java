package core.app.controller.restController;

import core.app.dto.AuthorDto;
import core.app.dto.AuthorSongGenreListDto;
import core.app.dto.GenreDto;
import core.app.dto.SongDto;
import core.app.model.Author;
import core.app.model.Genre;
import core.app.model.Song;
import core.app.service.abstraction.AuthorService;
import core.app.service.abstraction.GenreService;
import core.app.service.abstraction.SongService;
import core.app.service.abstraction.TrashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin/song/")
public class AdminSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSongRestController.class);
    private final SongService songService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private TrashService<Song> trashService;

    @Autowired
    public AdminSongRestController(SongService songService,
                                   AuthorService authorService,
                                   GenreService genreService,
                                   TrashService<Song> trashService) {
        this.songService = songService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.trashService = trashService;
    }

    /*
     * Необходимо переработать "в глубь" неверно, что мы сперва тянем сущности из БД, а затем парсим их в DTO
     * Необходимо сделать так, чтобы DAO возвращал список DTO
     */
    @GetMapping(value = "/all_songs")
    public List<SongDto> getAllSongs() {
        LOGGER.info("GET request '/all_songs'");
        List<SongDto> list = songService.getAllSong()
                .stream()
                .map(SongDto::new)
                .collect(Collectors.toList());
        LOGGER.info("Result has {} lines", list.size());
        return list;
    }

    @DeleteMapping(value = "/delete_song/{id}")
    public void deleteSong(@PathVariable("id") Long id) throws IOException, InterruptedException {
        LOGGER.info("DELETE request '/delete_song/{}'", id);
        Song song = songService.getById(id);
        trashService.moveToTrash(song);
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
            authorService.save(new Author(songDto.getAuthorName()));
            song.setAuthor(authorService.getByName(songDto.getAuthorName()));
        }
        Genre genre = genreService.getByName(songDto.getGenreName());
        if (genre != null) {
            song.setGenre(genre);
        }
        songService.save(song);
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/authors_songs_genres_for_today")
    public AuthorSongGenreListDto getAuthorSongGenreListForToday() {
        AuthorSongGenreListDto authorSongGenreListDto = new AuthorSongGenreListDto();
        Timestamp dateFrom = Timestamp.valueOf(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).minusNanos(0));
        Timestamp dateTo = Timestamp.valueOf(LocalDateTime.now());
        authorSongGenreListDto.setSongs(songService.getByCreatedDateRange(dateFrom, dateTo).stream().map(SongDto::new).collect(Collectors.toList()));
        authorSongGenreListDto.setAuthors(authorService.getByCreatedDateRange(dateFrom, dateTo).stream().map(AuthorDto::new).collect(Collectors.toList()));
        authorSongGenreListDto.setGenres(genreService.getByCreatedDateRange(dateFrom, dateTo).stream().map(GenreDto::new).collect(Collectors.toList()));
        return authorSongGenreListDto;
    }
}
