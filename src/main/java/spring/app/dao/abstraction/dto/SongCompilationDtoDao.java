package spring.app.dao.abstraction.dto;

import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;

import java.util.List;

public interface SongCompilationDtoDao {

    //TODO: implement
//    List<SongCompilationDto> getAll();

    List<SongDto> getAllSongsWithCompId(long compilationID);

    List<SongCompilationDto> getAllForAdmin();
}
