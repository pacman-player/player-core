package spring.app.service.abstraction;

import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;

import java.util.List;
import java.util.Set;

public interface SongQueueService  {
    void addSongQueue(SongQueue songQueue);
    void updateSongQueue(SongQueue songQueue);
    SongQueue getSongQueueBySongAndCompany(Song song, Company company);
    long getLastSongQueuesNumberFromCompany(Company company);
    void deleteAllSongQueues(Set<SongQueue> songQueues);
    void deletePlayedSong(Set<SongQueue> songQueues);
    void deleteById(Long id);
    List<SongQueue> getByCompanyId(Long id);
}
