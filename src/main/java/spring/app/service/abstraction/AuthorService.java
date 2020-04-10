package spring.app.service.abstraction;

import spring.app.model.Author;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorService extends GenericService<Author> {

    List<Author> getAllAuthor();

    Author getByName(String name);

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

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    boolean isExist(String name);
}
