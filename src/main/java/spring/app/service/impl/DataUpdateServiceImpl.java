package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.model.Tag;
import spring.app.service.abstraction.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Этот класс существует для наполнения БД из заказанных треков. genreNames - приходит из GenreDefineService.
 * Т.е. заносит в базу данные про артиста, песню и жанр
 * Возвращает id песни
 */
@Service
public class DataUpdateServiceImpl implements DataUpdateService {

    private final AuthorService authorService;
    private final SongService songService;
    private final TagService tagService;
    private final GenreService genreService;

    @Autowired
    public DataUpdateServiceImpl(AuthorService authorService,
                                 SongService songService,
                                 TagService tagService,
                                 GenreService genreService) {
        this.authorService = authorService;
        this.songService = songService;
        this.tagService = tagService;
        this.genreService = genreService;
    }

    @Override
    @Transactional
    public Song updateData(String authorName, String songName, String[] genreNames) {
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
                genreService.save(genre);
            }
            // записываем жанр в сет жанров
            authorGenres.add(genre);
        }
        // сохраняем жанры у автора в БД
        author.setAuthorGenres(authorGenres);
        authorService.update(author);

        Song song = songService.getByName(songName);
        if (song == null || !song.getAuthor().equals(author)) {
            // если песня новая или в БД у нее другой автор, то сохраняем ее в БД с новыми параметрами
            song = new Song(songName, author);
            Set<Tag> tags = tagService.findTags(authorName + ' ' + songName);
            song.setTags(tags);
            songService.save(song);
        }
        // возвращаем id песни
        //return song.getId();
        return song;

    }
}
