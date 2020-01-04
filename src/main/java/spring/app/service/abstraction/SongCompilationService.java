package spring.app.service.abstraction;

import spring.app.model.SongCompilation;

import java.util.List;

public interface SongCompilationService {
    void addSongСompilation(SongCompilation songCompilation);
    List<SongCompilation> getAllSongCompilations();
    List<SongCompilation> getListSongCompilationsByGenreId(Long id);

    void addSongCompilationToMorningPlaylist(Long id);
    List<SongCompilation> getAllCompilationsInMorningPlaylist();
}
