package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Genre;

@Repository
@Transactional
public class GenreDaoImpl extends AbstractDao<Long, Genre> implements GenreDao {
    GenreDaoImpl() {
        super(Genre.class);
    }
}
