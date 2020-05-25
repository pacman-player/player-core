package spring.app.dao.abstraction.dto;

import spring.app.dto.BotSongDto;
import spring.app.dto.SongDto;

import java.util.List;

public interface SongDtoDao {

    List<SongDto> getAll();

    List<SongDto> listOfSongsByName(String name);

    List<SongDto> listOfSongsByTag(String tag);

    SongDto getById(long songId);

    List<BotSongDto> getBySearchRequests(String author, String name);

}
