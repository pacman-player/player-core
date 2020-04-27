package spring.app.service.abstraction;

import spring.app.dto.AuthorDto;
import spring.app.model.Author;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorService {

    void addAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthorById(Long id);

    Author getByName(String name);

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
    List<AuthorDto> findAuthorsByNameContaining(String name);

    List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<AuthorDto> getAllAuthors();

    List<AuthorDto> getAllApprovedAuthors();

    List<Author> getApprovedAuthorsPage(int pageNumber, int pageSize);

    int getLastApprovedAuthorsPageNumber(int pageSize);

    boolean isExist(String name);
}
