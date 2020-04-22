package spring.app.service.abstraction;

import spring.app.model.Author;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorService extends GenericService<Long, Author>{

    Author getByName(String name);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Author> getAllApprovedAuthors();

    List<Author> getApprovedAuthorsPage(int pageNumber, int pageSize);

    int getLastApprovedAuthorsPageNumber(int pageSize);

    boolean isExist(String name);
}
