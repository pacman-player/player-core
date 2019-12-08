package spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/user")
@PropertySource("classpath:uploadedFilesPath.properties")
public class UserFileUploadRestController {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final SongService songService;

    @Value("${path}")
    String fileFolder;

    @Autowired
    public UserFileUploadRestController(GenreService genreService, AuthorService authorService, SongService songService) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.songService = songService;
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<String> fileUpload(
            @RequestParam("songAuthor") String songAuthor,
            @RequestParam("songGenre") String songGenre,
            @RequestParam("songName") String songName,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Выберите файл");
            }

            if (songAuthor.isEmpty() || songGenre.isEmpty() || songName.isEmpty()) {
                return ResponseEntity.badRequest().body("Поля не могут быть пустыми");
            }

            /*if (songService.isExist(songName)) {
                return ResponseEntity.badRequest().body("Песня с таким названием существует");
            }

            Genre genre = genreService.getByName(songGenre);
            if (genre == null) {
                genreService.addGenre(new Genre(songGenre));
                genre = genreService.getByName(songGenre);
            }

            Author author = authorService.getByName(songAuthor);
            if (author == null) {
                Author author1 = new Author(songAuthor);
                author1.getAuthorGenres().add(genre);
                authorService.addAuthor(author1);
                author = authorService.getByName(songAuthor);
            } else {
                author.getAuthorGenres().add(genre);
            }*/

            Genre genre = new Genre(songGenre);
            genreService.addGenre(new Genre(songGenre));
            genre = genreService.getByName(songGenre);

            Author author = new Author(songAuthor);
            author.getAuthorGenres().add(genre);
            authorService.addAuthor(author);
            author = authorService.getByName(songAuthor);

            Song song = new Song(songName);
            song.setAuthor(author);
            songService.addSong(song);
            song = songService.getByName(songName);

            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename().replaceAll(".*[.]", author.getId() + "_" + song.getId() + ".");
            Path path = Paths.get(fileFolder + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Ошибка ввода");
        }
        return ResponseEntity.ok("Файл добавлен");
    }
}