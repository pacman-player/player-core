package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongСompilationDao;
import spring.app.model.SongСompilation;
import spring.app.service.abstraction.SongСompilationService;



@Service
public class SongСompilationServiseImpl implements SongСompilationService {

    private SongСompilationDao songСompilationDao;

    @Autowired
    public SongСompilationServiseImpl(SongСompilationDao songСompilationDao) {
        this.songСompilationDao = songСompilationDao;
    }

    @Override
    public void addSongСompilation(SongСompilation songСompilation) {
        songСompilationDao.save(songСompilation);

    }
}
