package spring.app.service.abstraction;

import spring.app.model.OrderSong;

import java.sql.Timestamp;
import java.util.List;

public interface OrderSongService {

    void addSongOrder(OrderSong songOrder);
    long getSongOrdersByCompanyIdAndPeriod(Long id, Long period);
    long getSongOrdersByCompanyIdAndTimeRange(Long id, String period);

    long countAll(Long id);

    void bulkRemoveByCompany(Long userId);

    List<Long> getListSongIdByCompanyIdAndPeriod(Long id, Long period);
    List<Long> getListSongIdByCompanyIdAndTimeRange(Long id, String period);
}
