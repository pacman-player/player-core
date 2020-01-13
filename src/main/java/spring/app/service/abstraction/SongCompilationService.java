package spring.app.service.abstraction;

import spring.app.model.SongCompilation;

import java.util.List;

public interface SongCompilationService {
    void addSongCompilation(SongCompilation songCompilation);
    List<SongCompilation> getAllSongCompilations ();
    List<SongCompilation> getListSongCompilationsByGenreId(Long id);
    SongCompilation getSongCompilationByCompilationName(String compilationName);
    void updateSongCompilation(SongCompilation songCompilation);
    SongCompilation getSongCompilationById(Long id);
}
