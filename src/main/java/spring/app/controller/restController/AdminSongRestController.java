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

    @DeleteMapping(value = "/delete_song")
    public void deleteSong(@RequestBody Long id) {
        songService.deleteSongById(id);
    }

    @PostMapping(value = "/add_song")
    public void addSong(@RequestBody SongDto songDto) {
        //достаю песню по названию
        Song song = new Song(songDto.getName());
        //достаю автора(со своими полями) по имени
        Author author = authorService.getByName(songDto.getAuthor().getName());
        //если автор есть, сетим к песне
        if (author != null) {
            song.setAuthor(author);
        } else {
            //если автора по имени нет - создаем нового по этому же имени
            authorService.addAuthor(new Author(songDto.getAuthor().getName()));
            //сетим этого нового автора песне
            song.setAuthor(authorService.getByName(songDto.getAuthor().getName()));
        }
        //достаю жанр песни
        Genre genre = genreService.getByName(songDto.getGenre().getName());
        //если жанр есть, сетим к песне (по сути лишняя проверка тк жанр берем из бд)
        if (genre != null) {
            song.setGenre(genre);
        }
        //добавляем новую песню
        songService.addSong(song);
    }

    @PutMapping(value = "/update_song")
    public void updateSong(@RequestBody SongDto songDto) {
        //достаю исходную песню по id до изменения
        Song oldSong = songService.getSongById(songDto.getId());
        //достаю исходного автора до изменения
        Author author = oldSong.getAuthor();
        //достаю жанр по названию
        Genre genre = genreService.getByName(songDto.getGenre().getName());
        //создаю новую песню
        Song song = new Song(songDto.getId(), songDto.getName());
        //добавляю ей автора
        song.setAuthor(author);
        //добавляю ей жанр
        song.setGenre(genre);
        //обновляю
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
