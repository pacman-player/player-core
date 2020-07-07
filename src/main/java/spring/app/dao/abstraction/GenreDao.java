package spring.app.dao.abstraction;

import spring.app.model.Genre;
import spring.app.model.Song;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface GenreDao extends GenericDao<Long, Genre> {
    Genre getByName(String name);

    List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Genre> getAllApproved();

    List<Song> getSongsByGenre(Genre genreForDelete);

    long getDefaultGenreId();

    void deleteReferenceFromCompanyByGenre(Long id);

    void deleteReferenceFromOrgTypeByGenre(Long id);

    void deleteReferenceFromAuthorByGenre(Long id);

    BigInteger countOfGenresInOrgType(Long deletedGenreId);

    BigInteger countOfGenresInAuthor(Long deletedGenreId);

    void setDefaultGenre(Long id);

    void setDefaultGenreToOrgType(Long deleteGenreId, Long defaultGenreId);

    void setDefaultGenreToAuthor(Long genreId, Long defaultGenreId);
}