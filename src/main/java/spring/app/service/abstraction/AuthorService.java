package spring.app.service.abstraction;

import spring.app.model.Author;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    List<Author> getAllApprovedAuthors();

    List<Author> getApprovedAuthorsPage(int pageNumber, int pageSize);

    int getLastApprovedAuthorsPageNumber(int pageSize);

    Author getByName(String name);

    void addAuthor(Author author);

    /**
     * Возвращает всех авторов, у которых совпадает передаваемое значение
     *
     * @return list<Author>
     */
    List<Author> findAuthorsByNameContaining(String name);

    /**
     * Возвращает автора по id
     *
     * @return Author
     */
    Author getById(long authorsId);

    void updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    boolean isExist(String name);
}
