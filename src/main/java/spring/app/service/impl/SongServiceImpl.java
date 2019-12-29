package spring.app.service.impl;

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
    public void addSong(Song song) {
        songDao.save(song);
    }

    @Override
    public Song getByName(String name) {
        return songDao.getByName(name);
    }

    @Override
    public Song getBySongNameAndAuthorId(Long idAuthor, String songName) {
        return songDao.getBySongNameAndAuthorName(idAuthor,songName);
    }

    @Override
    public List<Song> getByAuthor(Long idAuthor) {
        return songDao.getByAuthor(idAuthor);
    }

    @Override
    public boolean isExist(String name) {
        return getByName(name) != null;
    }
}