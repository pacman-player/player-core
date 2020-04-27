package spring.app.dao.abstraction.dto;

import spring.app.dto.AuthorDto;

import java.util.List;

public interface AuthorDtoDao {

    List<AuthorDto> getAllAuthors();

    List<AuthorDto> getAllApproved();

    List<AuthorDto> findByNameContaining(String name);

}
