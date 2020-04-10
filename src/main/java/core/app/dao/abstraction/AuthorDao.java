package core.app.dao.abstraction;

import core.app.model.Author;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorDao extends GenericDao<Long, Author> {
    Author getByName(String name);

    boolean isExist(String name);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);
}
