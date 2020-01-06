package spring.app.service.abstraction;

import spring.app.model.SongCompilation;

import java.util.List;

public interface SongCompilationService {
    void addSongСompilation(SongCompilation songCompilation);
    List<SongCompilation> getAllSongCompilations();
    List<SongCompilation> getListSongCompilationsByGenreId(Long id);
    SongCompilation getSongCompilationByCompilationName(String compilationName);

    void addSongCompilationToMorningPlaylist(Long id);
    void addSongCompilationToMiddayPlaylist(Long id);
    void addSongCompilationToEveningPlaylist(Long id);

    List<SongCompilation> getAllCompilationsInMorningPlaylist();
    List<SongCompilation> getAllCompilationsInMiddayPlaylist();
    List<SongCompilation> getAllCompilationsInEveningPlaylist();

    SongCompilation getSongCompilationById(Long id);
}
