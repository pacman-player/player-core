package spring.app.service.abstraction;

import spring.app.dto.GenreDto;
import spring.app.model.Genre;

import java.sql.Timestamp;
import java.util.List;

public interface GenreService extends GenericService<Long, Genre> {

    Genre getByName(String name);

    List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<GenreDto> getAllGenreDto();

    List<Genre> getAllApprovedGenre();

    boolean isExistByName(String name);
}

