package spring.app.dao.abstraction;

import spring.app.model.Genre;

public interface GenreDao extends GenericDao<Long, Genre> {
    Genre getByName(String name);
}

