package spring.app.service.abstraction;

import spring.app.dto.GenreDto;
import spring.app.model.Genre;

import java.sql.Timestamp;
import java.util.List;

public interface GenreService extends GenericService<Long, Genre> {

    Genre getByName(String name);

    List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<GenreDto> getAllGenreDto();

    List<GenreDto> getAllApprovedGenreDto();

    long getDefaultGenreId();

    void saveBatch(List<Genre> genreList);

    boolean isExistByName(String name);

    List<String> getGenreNames(String keywords);

    void setDefaultGenre(Long id);

    void setDefaultGenreToOrgType(Long deleteGenreId, Long defaultGenreId);
}