package spring.app.service.impl;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void addGenre(Genre genre) {
        genreDao.save(genre);
    }

    @Override
    public Genre getByName(String name) {
        return genreDao.getByName(name);
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreDao.getAll();
    }

    @Override
    @Fetch(FetchMode.JOIN)
    public List<Genre> getAllGenreFetchModeJoin() {
        return genreDao.getAll();
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public void deleteGenreById(Long id) {
        genreDao.deleteById(id);
    }

    @Override
    public Genre getById(Long id) {
        return genreDao.getById(id);
    }
}