package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Genre;
import spring.app.model.SongCompilation;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class GenreDaoImpl extends AbstractDao<Long, Genre> implements GenreDao {
    GenreDaoImpl() {
        super(Genre.class);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT u FROM Genre u WHERE u.name = :name", Genre.class);
        query.setParameter("name", name);
        Genre genre;
        try {
            genre = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return genre;
    }
}