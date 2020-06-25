package spring.app.service.abstraction;

import spring.app.model.SongStatistic;

import java.util.List;

public interface SongStatisticService extends GenericService<Long, SongStatistic> {

    List<SongStatistic> getSortedTopList(int songsCount, String period);

    void saveOrUpdate(SongStatistic entity);

}
