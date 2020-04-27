package spring.app.dao.abstraction.dto;

import spring.app.dto.GenreDto;

import java.util.List;

public interface GenreDtoDao {

    List<GenreDto> getAll();

    boolean isExistByName(String name);

}
