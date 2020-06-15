package spring.app.dao.abstraction.dto;

import spring.app.dto.SongDto;

import java.util.List;

public interface SongDtoDao {

    List<SongDto> getAll();

    List<SongDto> listOfSongsByTag(String tag);

    SongDto getById(long songId);


    List<SongDto> getAllWithGenreByGenreIdDto(Long id);

    List<SongDto> getAllApprovedDto();

    List<SongDto> getSongsOutOfGenreDto(Long genreId);
}
