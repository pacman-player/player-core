package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.model.PlayList;
import spring.app.service.abstraction.PlayListService;

import java.util.List;

@Service
public class PlayListServiceImpl implements PlayListService {

    private PlayListDao playListDao;

    @Autowired
    public PlayListServiceImpl(PlayListDao playListDao) {
        this.playListDao = playListDao;
    }

    @Override
    public void addPlayList(PlayList playList) {
        playListDao.save(playList);
    }

    @Override
    public List<PlayList> getAllPlayList() {
        return playListDao.getAll();
    }
}
