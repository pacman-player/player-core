package spring.app.service.abstraction;

import spring.app.model.Genre;

import java.util.List;

public interface GenreService {

    void addGenre(Genre genre);

    List<Genre> getAllGenre();
    List<Genre> getAllGenreDaoJoin();

    List<Genre> getAllGenreFetchModeJoin();
    List<Genre> getAllGenreBatch();
    List<Genre> getAllGenreFetchModeSubselect();
    List<Genre> getAllGenreFetchModeSubselectBatch();

    Genre getByName(String name);

    Genre getById(Long id);

    Genre getByIdFetchModeJoin(Long id);

    void updateGenre(Genre genre);

    void deleteGenreById(Long id);
}
