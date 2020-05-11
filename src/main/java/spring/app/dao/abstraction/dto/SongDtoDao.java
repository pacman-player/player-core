package spring.app.dao.abstraction.dto;

import spring.app.dto.SongDto;
import spring.app.dto.SongDtoTop;

import java.sql.Timestamp;
import java.util.List;

public interface SongDtoDao {

    List<SongDto> getAll();

    List<SongDtoTop> getTopSongsByNumberOfList(Timestamp startTime,Timestamp endTime);

    SongDtoTop getSongDtoTopWithPoint(Timestamp startTime, Timestamp endTime, Long idSong);
}
