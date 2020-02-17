package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;
import spring.app.service.abstraction.SongQueueService;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SongQueueServiceImpl implements SongQueueService {

    private final SongQueueDao songQueueDao;

    @Autowired
    public SongQueueServiceImpl(SongQueueDao songQueueDao) {
        this.songQueueDao = songQueueDao;
    }

    @Override
    public void addSongQueue(SongQueue songQueue) {
        songQueueDao.save(songQueue);
    }
    @Override
    public void updateSongQueue(SongQueue songQueue) {
        songQueueDao.update(songQueue);
    }

    @Override
    public SongQueue getSongQueueBySongAndCompany(Song song, Company company) {
        return songQueueDao.getSongQueueBySongAndCompany(song, company);
    }

    @Override
    public long getLastSongQueuesNumberFromCompany(Company company) {
        return songQueueDao.getLastSongQueuesNumberFromCompany(company);
    }

    @Override
    public void deleteAllSongQueues(Set<SongQueue> songQueues) {
        songQueueDao.deleteAllSongQueues(songQueues);
    }

    @Override
    public void deletePlayedSong(Set<SongQueue> songQueues) {
        songQueueDao.deletePlayedSong(songQueues);
    }

    @Override
    public void deleteById(Long id) {
        songQueueDao.deleteById(id);
    }

    @Override
    public List<SongQueue> getByCompanyId(Long id) {
        return songQueueDao.getByCompanyId(id);
    }
}
