package spring.app.dao.abstraction.dto;

import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;

import java.util.List;

public interface SongCompilationDtoDao {

    List<SongCompilationDto> getAllDto();

    List<SongCompilationDto> getListSongCompilationsByGenreIdDto(Long id);

    SongCompilationDto getSongCompilationByIdDto(Long id);

    List<SongDto> getSongsDtoBySongCompilation(String compilationName);

    List<SongCompilationDto> getAllCompilationsPlaylistByCompanyIdDto(Long id, String namePlayList);

    List<SongDto> getAllSongsWithCompId(long compilationID);

    List<SongCompilationDto> getAllForAdmin();


}
