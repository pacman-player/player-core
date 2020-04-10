package core.app.service.impl;

import core.app.model.PlayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.app.dao.abstraction.PlayListDao;
import core.app.service.abstraction.PlayListService;

@Service
@Transactional
public class PlayListServiceImpl implements PlayListService {

    private final PlayListDao playListDao;

    @Autowired
    public PlayListServiceImpl(PlayListDao playListDao) {
        this.playListDao = playListDao;
    }

    @Override
    public void addPlayList(PlayList playList) {
        playListDao.save(playList);
    }

    @Override
    public PlayList getPlayList(Long id) {
        return playListDao.getById(id);
    }

    @Override
    public PlayList getPlayListByName(String name) {
        return playListDao.getByName(name);
    }
}
