package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;

import java.util.ArrayList;
import java.util.List;


@Service
public class SongCompilationServiceImpl implements SongCompilationService {

    private SongCompilationDao songCompilationDao;
    private UserDao userDao;

    @Autowired
    public SongCompilationServiceImpl(SongCompilationDao songCompilationDao) {
        this.songCompilationDao = songCompilationDao;
    }

    @Override
    public void addSongСompilation(SongCompilation songCompilation) {
        songCompilationDao.save(songCompilation);
    }

    @Override
    public List<SongCompilation> getAllSongCompilations() {
        return songCompilationDao.getAll();
    }

    @Override
    public List<SongCompilation> getListSongCompilationsByGenreId(Long id) {
        return songCompilationDao.getListSongCompilationsByGenreId(id);
    }
}
