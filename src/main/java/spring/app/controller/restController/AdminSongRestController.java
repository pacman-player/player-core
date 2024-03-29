package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.AuthorDto;
import spring.app.dto.AuthorSongGenreListDto;
import spring.app.dto.GenreDto;
import spring.app.dto.SongDto;
import spring.app.model.*;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongFileService;
import spring.app.service.abstraction.SongService;
import spring.app.util.ResponseBuilder;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin/song/")
public class AdminSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSongRestController.class);
    private final SongService songService;
    private final SongFileService songFileService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public AdminSongRestController(SongService songService, SongFileService songFileService, AuthorService authorService, GenreService genreService) {
        this.songService = songService;
        this.songFileService = songFileService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/all_songs")
    public List<SongDto> getAllSongsDto() {
        return songService.getAllSongsDto();
    }

    @DeleteMapping(value = "/delete_song/{id}")
    public void deleteSong(@PathVariable("id") Long id) {
        LOGGER.info("DELETE request '/delete_song/{}'", id);
        songService.deleteById(id);
    }

    /*
    Чтобы добавить новую песню сначала проверяю автора на наличие в бд.
     */
    @PostMapping(value = "/add_song")
    public Response<String> addSong(@ModelAttribute SongDto songDto) {
        LOGGER.info("POST request '/add_song'");

        ResponseBuilder<String> responseBuilder = new ResponseBuilder<>();

        if (songDto.getName() == null || songDto.getName().isEmpty()) {
            return responseBuilder.error("Введите название песни");
        }

        if (songDto.getAuthorName() == null || songDto.getAuthorName().isEmpty()) {
            return responseBuilder.error("Введите автора песни");
        }

        if (songDto.getSearchTags() == null || songDto.getSearchTags().size() == 0) {
            return responseBuilder.error("Заполните теги для добавляемой песни");
        }

        if (songDto.getFile() == null || songDto.getFile().isEmpty()) {
            return responseBuilder.error("Прикрепите файл с песней");
        }

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
            song.getAuthor().setAuthorGenres((Set<Genre>) genre);
                    //setGenre(genre);
        }
        songService.setTags(song, songDto.getSearchTags());
        songService.save(song);
        song = songService.getByName(song.getName());

        try {
            songFileService.saveSongFile(song, songDto.getFile());
        } catch (IOException e) {
            LOGGER.error("Error happened while saving new song file", e);
            return responseBuilder.error("Произошла ошибка при сохранении файла");
        } catch (IllegalArgumentException e) {
            return responseBuilder.error("Файл имеет некорректный формат, должен быть .mp3");
        }

        Response<String> success = responseBuilder.success("Песня успешно добавлена");
        return success;
    }

    /*
    Чтобы изменить песню сначала достаю исходную песню со всеми полями.
     */
    @PutMapping(value = "/update_song")
    public void updateSong(@RequestBody SongDto songDto) {
        LOGGER.info("PUT request '/update_song'");
        Song oldSong = songService.getById(songDto.getId());
        LOGGER.info("Changing Song = {}", oldSong);
        Author author = oldSong.getAuthor();
        Genre genre = genreService.getByName(songDto.getGenreName());
        Set<SongCompilation> songCompilations = oldSong.getSongCompilations();
        Song song = new Song(songDto.getId(), songDto.getName());
        Boolean isApproved = songDto.getApproved();
        song.setApproved(isApproved);
        song.setAuthor(author);
         song.setSongCompilations(songCompilations);
        //song.setGenre(genre);
        songService.setTags(song, songDto.getSearchTags());
        songService.update(song);
        LOGGER.info("Updated Song as = {}", song);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<SongDto> getSongById(@PathVariable(value = "id") Long id) {
        SongDto songDto = new SongDto(songService.getById(id));
        return ResponseEntity.ok(songDto);
    }

    @GetMapping(value = "/all_genre")
    @ResponseBody
    public List<GenreDto> getAllGenre() {
        return genreService.getAllGenreDto();
    }

    @GetMapping(value = "/genre/{id}")
    public List<SongDto> getAllSongsDto(@PathVariable(value = "id") Long id) {
        List<SongDto> list = songService.findSongsDtoByGenreId(id);
        return list;
    }

    /*
        Изменение авторов у нескольких песен
    */
    @PutMapping(value = "/update_genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateGenreOfSongs(@RequestBody Map<Integer, String> updateObject) {
        Genre newGenre = genreService.getByName(updateObject.get(-1));
        updateObject.forEach((key, value) -> {
            if (key != -1) {
                Song editSong = songService.getById(Long.parseLong(value));
                //editSong.setGenre(newGenre);
                songService.update(editSong);
            }
        });
    }

    @DeleteMapping(value = "/delete_tag_for_songs")
    public void deleteTagForSongs(@RequestBody Map<Integer, String> deleteObject) {
        Long tagId = Long.valueOf(deleteObject.remove(-1));
        List<Long> songIds = deleteObject.values().stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
        if (tagId == null || songIds == null || songIds.isEmpty()) {
            return;
        }
        songService.deleteTagForSongs(songIds, tagId);
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

    @GetMapping("/songs_by_tag")
    public List<SongDto> listOfSongsByTag(@RequestParam("tag") String tag) {
        return songService.listOfSongsByTag(tag);
    }
}
