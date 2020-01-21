package spring.app.service.impl;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

@Service
public class SongServiceImpl implements SongService {

    private final SongDao songDao;
    private final SongCompilationService songCompilationService;

    @Autowired
    public SongServiceImpl(SongDao songDao, SongCompilationService songCompilationService) {
        this.songDao = songDao;
        this.songCompilationService = songCompilationService;
    }

    @Override
//    @Fetch(FetchMode.JOIN)
    public List<Song> getAllSong() {
        return songDao.getAll();
    }

//    @Override
//    @Fetch(FetchMode.JOIN) //подгружаем внутренние объекты
//    public List<Song> getAllSongNonLazy() {
//        return songDao.getAll();
//    }

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

//    @Override
//    public Song getSongById(long id) {
//        return songDao.getById(id);
//    }

    @Override
    public boolean isExist(String name) {
        return getByName(name) != null;
    }

    @Override
    public List<Song> getAllSongInSongCompilation(Long id) {
        System.out.println("+++ start +++");
        System.out.println("+++ before reset +++");
        SQLStatementCountValidator.reset();
        System.out.println("+++ after reset +++");
        System.out.println("+++ before query +++");
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(id);
        System.out.println("+++ after query +++");
        System.out.println("+++ before assert +++");
        assertSelectCount(1);
        System.out.println("+++ after assert +++");
        Set<Song> allSongSet = songCompilation.getSong();
        List<Song> allSongList = new ArrayList<>(allSongSet);
        return allSongList;
    }

    @Override
    public void updateSong(Song song) {
        songDao.update(song);
    }

//    @Override
//    @Fetch(FetchMode.JOIN) //подгружаем внутренние объекты
//    public void updateSongNonLazy(Song song) {
//        songDao.update(song);
//    }

    @Override
    public Song getSongById(Long id) {
        return songDao.getById(id);
    }
}