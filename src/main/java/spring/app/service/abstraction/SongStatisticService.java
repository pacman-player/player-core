package spring.app.service.abstraction;

import spring.app.model.SongStatistic;

import java.util.List;

public interface SongStatisticService extends GenericService<Long, SongStatistic> {

    Long getOrderCountBySongName(String songName);

    List<SongStatistic> getSortedTopList(int songsCount, String period);

    SongStatistic getRecordById(Long id);

    public void saveOrUpdate(SongStatistic entity);

}
