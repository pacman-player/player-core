package spring.app.dao.abstraction;

import spring.app.model.OrderSong;

import java.sql.Timestamp;
import java.util.List;

public interface OrderSongDao extends GenericDao<Long, OrderSong> {

    long getSongOrdersByCompanyIdAndPeriod(Long id, Timestamp after);
    long getSongOrdersByCompanyIdAndTimeRange(Long id, Timestamp start, Timestamp end);
    long countAll(Long id);

    List<Long> getListSongIdByCompanyIdAndPeriod(Long id, Timestamp after);
    List<Long> getListSongIdByCompanyIdAndTimeRange(Long id, Timestamp start, Timestamp end);
}
