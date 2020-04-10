package core.app.service.impl;

import core.app.model.Author;
import core.app.model.Genre;
import core.app.model.Song;
import core.app.service.abstraction.AuthorService;
import core.app.service.abstraction.GenreService;
import core.app.service.abstraction.SongService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.app.service.abstraction.DataUpdateService;

import java.util.HashSet;
import java.util.Set;

/**
 * Этот класс существует для наполнения БД из заказанных треков. genreNames - приходит из GenreDefineService.
 * Т.е. заносит в базу данные про артиста, песню и жанр
 * Возвращает id песни
 */
@Service
@Transactional
public class DataUpdateServiceImpl implements DataUpdateService {

    private AuthorService authorService;
    private SongService songService;
    private GenreService genreService;

    public DataUpdateServiceImpl(AuthorService authorService,
                                 SongService songService,
                                 GenreService genreService) {
        this.authorService = authorService;
        this.songService = songService;
        this.genreService = genreService;
    }

    @Override
    public Long updateData(String authorName, String songName, String[] genreNames) {
        Set<Genre> authorGenres;

        Author author = authorService.getByName(authorName);
        if (author == null) { // если автора нет в БД, тогда сохраняем нового
            // и присваиваем переменной authorGenres пустой сет жанров
            author = new Author(authorName);
            authorService.save(author);
            authorGenres = new HashSet<>();
        } else {
            // если автор есть, записываем его жанры в переменную
            authorGenres = author.getAuthorGenres();
        }

        for (String genreName : genreNames) {
            Genre genre = genreService.getByName(genreName);
            if (genre == null) {
                // добавляем новые жанры если мы о них не знаем
                genre = new Genre(genreName);
                genreService.addGenre(genre);
            }
            // записываем жанр в сет жанров
            authorGenres.add(genre);
        }
        // сохраняем жанры у автора в БД
        author.setAuthorGenres(authorGenres);
        authorService.updateAuthor(author);

        Song song = songService.getByName(songName);
        if (song == null || !song.getAuthor().equals(author)) {
            // если песня новая или в БД у нее другой автор, то сохраняем ее в БД с новыми параметрами
            song = new Song(songName, author, (Genre) authorGenres.toArray()[0]);
            songService.save(song);
        }
        // возвращаем id песни
        return song.getId();
    }
}
