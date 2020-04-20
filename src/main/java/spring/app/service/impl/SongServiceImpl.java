package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.model.SongQueue;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SongServiceImpl extends AbstractServiceImpl<Song, SongDao> implements SongService {
    private final SongCompilationService songCompilationService;

    @Autowired
    public SongServiceImpl(SongDao dao, SongCompilationService songCompilationService) {
        super(dao);
        this.songCompilationService = songCompilationService;
    }

    @Override
    public void addSong(Song song) {
        dao.save(song);
    }

    @Override
    public void updateSong(Song song) {
        dao.update(song);
    }

    @Override
    public void deleteSongById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public boolean isExist(String name) {
        return dao.isExist(name);
    }

    @Override
    public Song getSongById(Long id) {
        return dao.getById(id);
    }

    @Override
    public Song getByName(String name) {
        return dao.getByName(name);
    }

    //TODO: кандидат на удаление, не используется
//    @Override
//    public Song getByAuthorAndName(String author, String name) {
//        return songDao.getByAuthorAndName(author, name);
//    }

    @Override
    public Song getBySearchRequests(String author, String name) {
        return dao.getBySearchRequests(author, name);
    }

    @Override
    public List<Song> getAllSongs() {
        return dao.getAll();
    }

    @Override
    public List<Song> findSongsByNameContaining(String name) {
        return dao.findByNameContaining(name);
    }

    @Override
    public List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return dao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<Song> getAllSongInSongCompilation(Long id) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(id);
        Set<Song> allSongSet = songCompilation.getSong();
        return new ArrayList<>(allSongSet);
    }

    @Override
    public List<Song> getAllApprovedSongs() {
        return dao.getAllApproved();
    }

    @Override
    public List<Song> getApprovedSongsPage(int pageNumber, int pageSize) {
        return dao.getApprovedPage(pageNumber, pageSize);
    }

    @Override
    public int getLastApprovedSongsPageNumber(int pageSize) {
        return dao.getLastApprovedPageNumber(pageSize);
    }

    @Override
    public Long getSongIdByAuthorAndName(String author, String name) {
        return dao.getSongIdByAuthorAndName(author, name);
    }

    @Override
    public Long getAuthorIdBySongId(Long songId) {
        return dao.getAuthorIdBySongId(songId);
    }
}