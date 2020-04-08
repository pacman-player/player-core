package spring.app.dao.abstraction;

import spring.app.model.Genre;

import java.sql.Timestamp;
import java.util.List;

public interface GenreDao extends GenericDao<Long, Genre> {
    Genre getByName(String name);

    List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Genre> getAllApproved();
}

