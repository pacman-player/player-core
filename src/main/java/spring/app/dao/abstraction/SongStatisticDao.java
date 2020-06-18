package spring.app.dao.abstraction;

import spring.app.model.SongStatistic;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface SongStatisticDao extends GenericDao<Long, SongStatistic> {

    Long getOrderCountBySongName(String songName);

    List<SongStatistic> getSortedTopListForDay(int songsCount);

    List<SongStatistic> getSortedTopListForRange(int songsCount, Date orderDate);

    SongStatistic getSongStatisticByNameAndDate(String songName, Date orderDate);
}
