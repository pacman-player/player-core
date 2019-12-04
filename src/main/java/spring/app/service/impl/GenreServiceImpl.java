package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;

import java.util.List;

@Component
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void addGenre(Genre genre) {
        genreDao.save(genre);
    }

    @Override
    public List<Genre> getAllGenre() {
       /* List<Genre> list = genreDao.getAll();
        for (Genre l : list){
            System.out.println(l.toString());
        }*/
        return genreDao.getAll();
    }
}
