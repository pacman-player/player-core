package core.app.service.abstraction;

import core.app.model.Author;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorService extends GenericService<Author> {

    Author getByName(String name);

    void deleteAuthorById(Long id);

    /**
     * Возвращает автора по id
     *
     * @return Author
     */
    Author getById(long authorsId);

    /**
     * Возвращает всех авторов, у которых совпадает передаваемое значение
     *
     * @return list<Author>
     */
    List<Author> findAuthorsByNameContaining(String name);

    void updateAuthor(Author author);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Author> getAllAuthors();

    List<Author> getAllApprovedAuthors();

    List<Author> getApprovedAuthorsPage(int pageNumber, int pageSize);

    int getLastApprovedAuthorsPageNumber(int pageSize);

    boolean isExist(String name);
}
