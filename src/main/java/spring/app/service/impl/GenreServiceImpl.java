package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;
import spring.app.util.CrudInterceptor;

import java.util.List;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    CrudInterceptor crudInterceptor = new CrudInterceptor();

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
    public List<Genre> getAllGenreFetchModeJoin() {
        return null;
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