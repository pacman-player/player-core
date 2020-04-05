package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class SongServiceImpl implements SongService {
    private final SongDao songDao;
    private final SongCompilationService songCompilationService;

    @Autowired
    public SongServiceImpl(SongDao songDao, SongCompilationService songCompilationService) {
        this.songDao = songDao;
        this.songCompilationService = songCompilationService;
    }

    @Override
    public List<Song> getAllSong() {
        return songDao.getAll();
    }

    @Override
    public void deleteSongById(Long id) {
        songDao.deleteById(id);
    }

    @Override
    public void addSong(Song song) {
        songDao.save(song);
    }

    @Override
    public Song getByName(String name) {
        return songDao.getByName(name);
    }

    @Override
    public Song getByAuthorAndName(String author, String name) {
        return songDao.getByAuthorAndName(author, name);
    }

    @Override
    public boolean isExist(String name) {
        return songDao.isExist(name);
    }

    @Override
    public List<Song> findSongsByNameContaining(String name) {
        return songDao.findByNameContaining(name);
    }

    @Override
    public Song getById(long songId) {
        return songDao.getById(songId);
    }

    @Override
    public Long getSongIdByAuthorAndName(String author, String name) {
        return songDao.getSongIdByAuthorAndName(author, name);
    }

    @Override
    public Long getAuthorIdBySongId(Long songId) {
        return songDao.getAuthorIdBySongId(songId);
    }

    @Override
    public List<Song> getAllSongInSongCompilation(Long id) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(id);
        Set<Song> allSongSet = songCompilation.getSong();
        return new ArrayList<>(allSongSet);
    }

    @Override
    public void updateSong(Song song) {
        songDao.update(song);
    }

    @Override
    public Song getSongById(Long id) {
        return songDao.getById(id);
    }
}