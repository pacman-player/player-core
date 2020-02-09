package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.DataUpdateService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongService;

import java.util.HashSet;
import java.util.Set;

/**
 * Заносит в базу данные про артиста, песню и жанр
 * Возвращает id песни
 */
@Service
@Transactional
public class DataUpdateServiceImpl implements DataUpdateService {

    private final AuthorService authorService;
    private final SongService songService;
    private GenreService genreService;

    public DataUpdateServiceImpl(AuthorService authorService, SongService songService, GenreService genreService) {
        this.authorService = authorService;
        this.songService = songService;
        this.genreService = genreService;
    }

    @Override
    public Long updateData(String authorName, String songName, String[] genreNames) {

        Set<Genre> authorGenres = new HashSet<>();
        for (String genreName : genreNames) {
            Genre genre = genreService.getByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                genreService.addGenre(genre);
            }
            authorGenres.add(genre);
        }


        Author author = authorService.getByName(authorName);
        if (author == null) {
            author = new Author(authorName);
            author.setAuthorGenres(authorGenres);
            authorService.addAuthor(author);
        }

        Song song = songService.getByName(songName);
        if (song == null || !song.getAuthor().equals(author)) {
            song = new Song(songName, author,(Genre)authorGenres.toArray()[0]);
            songService.addSong(song);
        }

        return song.getId();
    }
}
