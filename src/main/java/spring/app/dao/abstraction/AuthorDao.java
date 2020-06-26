package spring.app.dao.abstraction;

import spring.app.model.Author;
import spring.app.model.Genre;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface AuthorDao extends GenericDao<Long, Author> {
    Author getByName(String name);

    boolean isExist(String name);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Author> getApprovedPage(int pageNumber, int pageSize);

    int getLastApprovedPageNumber(int pageSize);

    void setDefaultGenre(long genreId, long defaultGenreId);
}
