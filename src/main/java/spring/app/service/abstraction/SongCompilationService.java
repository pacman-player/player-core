package spring.app.service.abstraction;

import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;
import spring.app.model.Song;
import spring.app.model.SongCompilation;

import java.io.IOException;
import java.util.List;


public interface SongCompilationService extends GenericService<Long, SongCompilation> {

    List<SongCompilationDto> getAllSongCompilationDto();

    List<SongCompilation> getListSongCompilationsByGenreId(Long id);

    List<SongDto> getSongCompilationContentById(Long compilationId);

    void deleteValByGenreId(Long id);

    void removeSongFromSongCompilation(Long compilationId, Long songId);

    void addSongToSongCompilation(Long compilationId, Long songId);

    List<Song> getAvailableSongsForCompilationById(Long compilationId);

    void addSongCompilationToMorningPlaylist(Long id);

    void addSongCompilationToMiddayPlaylist(Long id);

    void addSongCompilationToEveningPlaylist(Long id);

    List<SongCompilation> getAllCompilationsInMorningPlaylistByCompanyId(Long id);

    List<SongCompilation> getAllCompilationsInMiddayPlaylistByCompanyId(Long id);

    List<SongCompilation> getAllCompilationsInEveningPlaylistByCompanyId(Long id);

    SongCompilation getSongCompilationById(Long id);

    SongCompilation getSongCompilationByCompilationName(String compilationName);

    void deleteSongCompilationFromPlayList(Long id, String dayTime);

    void deleteSongCompilation(SongCompilation songCompilation) throws IOException;

    List<SongCompilationDto> getAllDto();

    List<SongCompilationDto> getListSongCompilationsByGenreIdDto(Long id);

    SongCompilationDto getSongCompilationByIdDto(Long id);

    List<SongDto> getSongsDtoBySongCompilation(String compilationName);

    List<SongCompilationDto> getAllCompilationsPlaylistByCompanyIdDto(Long id, String namePlayList);
}
