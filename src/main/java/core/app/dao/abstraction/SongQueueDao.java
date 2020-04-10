package core.app.dao.abstraction;

import core.app.model.Company;
import core.app.model.Song;
import core.app.model.SongQueue;

import java.util.List;
import java.util.Set;

public interface SongQueueDao extends GenericDao<Long, SongQueue> {
    SongQueue getSongQueueBySongAndCompany(Song song, Company company);

    long getLastSongQueuesNumberFromCompany(Company company);

    void deleteAllSongQueues(Set<SongQueue> songQueues);

    void deletePlayedSong(Set<SongQueue> songQueues);

    List<SongQueue> getByCompanyId(Long id);
}
