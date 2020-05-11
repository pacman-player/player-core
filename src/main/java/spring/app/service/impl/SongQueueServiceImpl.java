package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.model.SongQueue;
import spring.app.service.abstraction.SongQueueService;

import java.util.List;
import java.util.Set;

@Service
public class SongQueueServiceImpl extends AbstractServiceImpl<Long, SongQueue, SongQueueDao> implements SongQueueService {

    @Autowired
    public SongQueueServiceImpl(SongQueueDao dao) {
        super(dao);
    }

    @Override
    public SongQueue getSongQueueBySongAndCompany(Song song, Company company) {
        return dao.getSongQueueBySongAndCompany(song, company);
    }

    @Override
    public long getLastSongQueuesNumberFromCompany(Company company) {
        return dao.getLastSongQueuesNumberFromCompany(company);
    }

    @Override
    public void deleteAllSongQueues(Set<SongQueue> songQueues) {
        dao.deleteAllSongQueues(songQueues);
    }

    @Override
    public void deletePlayedSong(Set<SongQueue> songQueues) {
        dao.deletePlayedSong(songQueues);
    }

    @Override
    public List<SongQueue> getByCompanyId(Long id) {
        return dao.getByCompanyId(id);
    }
}
