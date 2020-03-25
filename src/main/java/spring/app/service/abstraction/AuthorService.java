package spring.app.service.abstraction;

import spring.app.model.Author;
import spring.app.model.Genre;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthor();

    List<Author> getAllApprovedAuthor();

    Author getByName(String name);

    void addAuthor(Author author);

    /**
     * Возвращает всех авторов, у которых совпадает передаваемое значение
     * @return list<Author>
     */
    List<Author> findAuthorsByNameContaining(String name);

    /**
     * Возвращает автора по id
     * @return Author
     */
    Author getById(long authorsId);

    void updateAuthor(Author author);

    void deleteAuthorById(Long id);

    boolean isExist(String name);
}
