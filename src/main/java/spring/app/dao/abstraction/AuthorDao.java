package spring.app.dao.abstraction;

import spring.app.model.Author;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface AuthorDao extends GenericDao<Long, Author> {
    Author getByName(String name);

    boolean isExist(String name);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Author> getApprovedPage(int pageNumber, int pageSize);

    int getLastApprovedPageNumber(int pageSize);
}
