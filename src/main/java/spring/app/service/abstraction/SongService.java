package spring.app.service.abstraction;

import spring.app.dto.BotSongDto;
import spring.app.dto.SongDto;
import spring.app.model.Song;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface SongService extends GenericService<Long, Song> {

    boolean isExist(String name);

    Song getByName(String name);

    //TODO: кандидат на удаление, не используется
//    Song getByAuthorAndName(String author, String name);

    List<BotSongDto> getBySearchRequests(String author, String name, Long companyId);

    List<SongDto> getAllSongsDto();

    List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<SongDto> getAllSongsByCompilationId(Long id);

    List<Song> getAllApprovedSongs();

    List<Song> getApprovedSongsPage(int pageNumber, int pageSize);

    /**
     * Возвращает песню по id жанра
     *
     * @return List<Song>
     */
    List<SongDto> findSongsDtoByGenreId(Long id);

    int getLastApprovedSongsPageNumber(int pageSize);

    Long getSongIdByAuthorAndName(String author, String name);

    Long getAuthorIdBySongId(Long songId);

    void setTags(Song song, String tagString);

    void setTags(Song song, Set<String> stringTags);

    List<SongDto> listOfSongsDtoByName(String name);

    List<SongDto> listOfSongsByTag(String tag);

    void deleteTagForSongs(List<Long> songIds, Long tagId);

    void resetSongCounter(long songId);

    int getSongCounterVal(String trackName);

    List<SongDto> getAllApprovedSongsDto();

    SongDto getSongDtoById(Long id);

}
