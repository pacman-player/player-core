package spring.app.dao.abstraction;

import spring.app.model.SongCompilation;

import java.util.List;

public interface SongCompilationDao extends GenericDao<Long, SongCompilation> {
    List<SongCompilation> getListSongCompilationsByGenreId(Long id);

//    List<SongCompilation> getAllCompilationsInMorningPlaylist();
//    void addSongCompilationToMorningPlaylist(Long id);
}
