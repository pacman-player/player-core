package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.SongQueue;
import spring.app.service.abstraction.SongQueueService;

@Service
public class SongQueueImpl implements SongQueueService {

    private final SongQueueDao songQueueDao;

    @Autowired
    public SongQueueImpl(SongQueueDao songQueueDao) {
        this.songQueueDao = songQueueDao;
    }

    @Override
    public void addSongQueue(SongQueue songQueue) {
        songQueueDao.save(songQueue);
    }
}
