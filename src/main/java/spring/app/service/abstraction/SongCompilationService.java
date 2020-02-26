package spring.app.service.abstraction;

import spring.app.model.SongCompilation;

import java.io.IOException;
import java.util.List;

public interface SongCompilationService {
    void addSongCompilation(SongCompilation songCompilation);
    List<SongCompilation> getAllSongCompilations();
    List<SongCompilation> getListSongCompilationsByGenreId(Long id);
    void deleteValByGenreId(Long id);

    void addSongCompilationToMorningPlaylist(Long id);
    void addSongCompilationToMiddayPlaylist(Long id);
    void addSongCompilationToEveningPlaylist(Long id);

    List<SongCompilation> getAllCompilationsInMorningPlaylistByCompanyId(Long id);
    List<SongCompilation> getAllCompilationsInMiddayPlaylistByCompanyId(Long id);
    List<SongCompilation> getAllCompilationsInEveningPlaylistByCompanyId(Long id);

    SongCompilation getSongCompilationById(Long id);
    SongCompilation getSongCompilationByCompilationName(String compilationName);

    void deleteSongCompilationFromPlayList(Long id, String dayTime);

    void updateCompilation(SongCompilation songCompilation);

    void deleteSongCompilation(SongCompilation songCompilation) throws IOException;
}
