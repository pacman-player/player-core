package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.FileUploadService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final SongService songService;

    @Value("${music.path}")
    private String fileFolder;

    @Autowired
    public FileUploadServiceImpl(GenreService genreService, AuthorService authorService, SongService songService) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.songService = songService;
    }

    @Override
    public ResponseEntity<String> upload(String songAuthor, String songGenre, String songName, MultipartFile file)
            throws UnsupportedEncodingException {
        if (songAuthor.isEmpty() || songGenre.isEmpty() || songName.isEmpty()) {
            return new ResponseEntity<>(encode("Поля не могут быть пустыми"), HttpStatus.BAD_REQUEST);
        }

        if (file.isEmpty()) {
            return new ResponseEntity<>(encode("Выберите файл"), HttpStatus.BAD_REQUEST);
        }

        if (songService.isExist(songName)) {
            return new ResponseEntity<>(encode("Файл с таким названием существует"), HttpStatus.BAD_REQUEST);
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
        }

        Song song = new Song(songName);
        song.setAuthor(author);
        song.setGenre(genre);
        songService.addSong(song);
        song = songService.getByName(songName);

        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename().replaceAll(".*[.]", author.getId() + "_" + song.getId() + ".");
            Path path = Paths.get(fileFolder + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            return new ResponseEntity<>(encode("Ошибка ввода вывода"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(encode("Файл добавлен"), HttpStatus.OK);
    }

    private String encode(String line) throws UnsupportedEncodingException {
        return URLEncoder.encode(line, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");
    }
}
