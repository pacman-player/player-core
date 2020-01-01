package spring.app.controller.restController;

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

@RestController
@RequestMapping("/api/admin/song/")
public class AdminSongRestController {
    private final SongService songService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public AdminSongRestController(SongService songService, AuthorService authorService, GenreService genreService) {
        this.songService = songService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_songs")
    @ResponseBody
    public List<Song> getAllSongs() {
        List<Song> list = songService.getAllSong();
        return list;
    }

    @DeleteMapping(value = "/delete_song/{id}")
    public void deleteSong(@PathVariable("id") Long id) {
        songService.deleteSongById(id);
    }

    /*
    Чтобы добавить новую песню сначала проверяю автора на наличие в бд.
     */
    @PostMapping(value = "/add_song")
    public void addSong(@RequestBody SongDto songDto) {
        Song song = new Song(songDto.getName());
        Author author = authorService.getByName(songDto.getAuthor().getName());
        if (author != null) {
            song.setAuthor(author);
        } else {
            authorService.addAuthor(new Author(songDto.getAuthor().getName()));
            song.setAuthor(authorService.getByName(songDto.getAuthor().getName()));
        }
        Genre genre = genreService.getByName(songDto.getGenre().getName());
        if (genre != null) {
            song.setGenre(genre);
        }
        songService.addSong(song);
    }

    /*
    Чтобы изменить песню сначала достаю исходную песню со всеми полями.
     */
    @PutMapping(value = "/update_song")
    public void updateSong(@RequestBody SongDto songDto) {
        Song oldSong = songService.getSongById(songDto.getId());
        Author author = oldSong.getAuthor();
        Genre genre = genreService.getByName(songDto.getGenre().getName());
        Song song = new Song(songDto.getId(), songDto.getName());
        song.setAuthor(author);
        song.setGenre(genre);
        songService.updateSong(song);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Song> getSongById(@PathVariable(value = "id") Long id) {
        Song song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<Genre> getAllGenre() {
        List<Genre> list = genreService.getAllGenre();
        return list;
    }
}
