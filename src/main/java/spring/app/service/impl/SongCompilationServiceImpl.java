package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.model.Company;
import spring.app.model.PlayList;
import spring.app.model.SongCompilation;
import spring.app.model.User;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SongCompilationServiceImpl implements SongCompilationService {

    private SongCompilationDao songCompilationDao;
    private UserService userService;

    @Autowired
    public SongCompilationServiceImpl(SongCompilationDao songCompilationDao, UserService userService) {
        this.songCompilationDao = songCompilationDao;
        this.userService = userService;
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

    @Override
    public SongCompilation getSongCompilationByCompilationName(String compilationName) {
        return songCompilationDao.getSongCompilationByCompilationName(compilationName);
    }
}
