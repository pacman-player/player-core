package spring.app.service.abstraction;

import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.model.Genre;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AuthorService extends GenericService<Long, Author> {

    Author getByName(String name);

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

    List<AuthorDto> getAuthorsOutOfGenre(Long genreID);

    List<AuthorDto> getAuthorsOfGenre(Long genreID);

    Set<Author> getUpdateAuthorsOfGenre(Genre genre, Map<Integer, String> updateAuthors);
}
