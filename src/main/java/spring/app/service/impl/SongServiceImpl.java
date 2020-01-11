package spring.app.service.impl;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;
import spring.app.service.abstraction.SongService;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongDao songDao;

    @Autowired
    public SongServiceImpl(SongDao songDao) {
        this.songDao = songDao;
    }

    @Override
    @Fetch(FetchMode.JOIN) //подгружаем внутренние объекты
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
    public boolean isExist(String name) {
        return getByName(name) != null;
    }

    @Override
    @Fetch(FetchMode.JOIN) //подгружаем внутренние объекты
    public void updateSong(Song song) {
        songDao.update(song);
    }

    @Override
    public Song getSongById(Long id) {
        return songDao.getById(id);
    }
}