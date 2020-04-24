package spring.app.dao.abstraction;

import spring.app.model.Genre;
import spring.app.model.Song;

import java.sql.Timestamp;
import java.util.List;

public interface GenreDao extends GenericDao<Long, Genre> {
    Genre getByName(String name);

    List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Genre> getAllApproved();

    List<Song> getSongsByGenre(Genre genreForDelete);

    void deleteReferenceFromCompanyByGenre(Genre genre);

    void deleteReferenceFromOrgTypeByGenre(Genre genre);

    void flush();
}

