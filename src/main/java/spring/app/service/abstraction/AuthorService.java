package spring.app.service.abstraction;

import spring.app.model.Author;

import java.util.List;

public interface AuthorService {
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
}
