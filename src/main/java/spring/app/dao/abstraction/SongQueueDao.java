package spring.app.dao.abstraction;

import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;

public interface SongQueueDao extends GenericDao<Long, SongQueue> {
    SongQueue getSongQueueBySongAndCompany(Song song, Company company);

    long getLastSongQueuesNumberFromCompany(Company company);
}
