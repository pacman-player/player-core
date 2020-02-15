package spring.app.dao.abstraction;

import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;

import java.util.List;
import java.util.Set;

public interface SongQueueDao extends GenericDao<Long, SongQueue> {
    SongQueue getSongQueueBySongAndCompany(Song song, Company company);

    long getLastSongQueuesNumberFromCompany(Company company);

    void deleteAllSongQueues(Set<SongQueue> songQueues);

    void deletePlayedSong(Set<SongQueue> songQueues);

    List<SongQueue> getByCompanyId(Long id);
}
