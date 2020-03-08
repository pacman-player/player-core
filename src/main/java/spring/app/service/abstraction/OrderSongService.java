package spring.app.service.abstraction;

import spring.app.model.OrderSong;

import java.sql.Timestamp;

public interface OrderSongService {

    void addSongOrder(OrderSong songOrder);
    long getSongOrdersByCompanyIdAndPeriod(Long id, Long period);
    long getSongOrdersByCompanyIdAndTimeRange(Long id, String period);

    long countAll(Long id);

    void bulkRemoveByCompany(Long userId);
}
