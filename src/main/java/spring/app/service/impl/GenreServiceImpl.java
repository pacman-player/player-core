package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;
    private final SongCompilationService songCompilationService;


    @Autowired
    public GenreServiceImpl(GenreDao genreDao, SongCompilationService songCompilationService) {
        this.genreDao = genreDao;
        this.songCompilationService = songCompilationService;
    }

    @Transactional
    @Override
    public void addGenre(Genre genre) {
        genreDao.save(genre);
    }

    @Transactional
    @Override
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Transactional
    @Override
    public void deleteGenreById(Long id) {
        Genre notDefinedGenre = getByName("not defined");
        Genre genreForDelete = getById(id);
        // в сервисе только таким образом можно пробежать по всем авторам , удалить у них наш жанр и в случае если жанров у них больше нет добавить "не определенный"
        List<Author> authors = new ArrayList<>(genreForDelete.getAuthors());
        for (Author author : authors) {
            author.getAuthorGenres().remove(genreForDelete);
            if (author.getAuthorGenres().size() == 0) {
                author.getAuthorGenres().add(notDefinedGenre);
            }
        }

        List<Song> songs = genreDao.getSongsByGenre(genreForDelete);
        for (Song song : songs) {
            song.setGenre(notDefinedGenre);
        }
        genreDao.deleteReferenceFromOrgTypeByGenre(genreForDelete);
        genreDao.deleteReferenceFromCompanyByGenre(genreForDelete);

        List<SongCompilation> songCompilationList = new ArrayList<>(genreForDelete.getSongCompilation());
        for (SongCompilation songCompilation : songCompilationList) {
            songCompilation.setGenre(notDefinedGenre);
            genreForDelete.getSongCompilation().remove(songCompilation);
        }
        genreDao.flush();
        genreDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getById(Long id) {
        return genreDao.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getByName(String name) {
        return genreDao.getByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return genreDao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenre() {
        return genreDao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllApprovedGenre() {
        return genreDao.getAllApproved();
    }
}