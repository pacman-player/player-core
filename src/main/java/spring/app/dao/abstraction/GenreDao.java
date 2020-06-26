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

    void deleteReferenceFromCompanyByGenre(long id);

    void deleteReferenceFromOrgTypeByGenre(long id);

    BigInteger countOfGenresInOrgType(long deletedGenreId);

    void setDefaultGenre(long id);

    void setDefaultGenreToOrgType(long deleteGenreId, long defaultGenreId);

    void deleteDefaultGenre();

    void flush();
}