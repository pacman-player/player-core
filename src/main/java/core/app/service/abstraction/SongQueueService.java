package core.app.service.abstraction;

import core.app.model.Company;
import core.app.model.Song;
import core.app.model.SongQueue;

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
