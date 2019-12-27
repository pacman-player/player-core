package spring.app.service.abstraction;

import spring.app.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthor();

    Author getByName(String name);

    void addAuthor(Author author);

    /**
     * Возвращает всех авторов
     * @return list<Author>
     */
    List<Author> getAllAuthors();

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
}
