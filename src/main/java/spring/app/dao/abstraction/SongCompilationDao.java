package spring.app.dao.abstraction;

import spring.app.model.Song;
import spring.app.model.SongCompilation;

import java.util.List;

public interface SongCompilationDao extends GenericDao<Long, SongCompilation> {
    List<SongCompilation> getListSongCompilationsByGenreId(Long id);

    // Получить содержимое подборки (все песни из подборки)
    List<Song> getSongCompilationContentById(Long compilationId);

    void deleteValByGenreId(Long id);

    SongCompilation getSongCompilationByCompilationName(String compilationName);
}
