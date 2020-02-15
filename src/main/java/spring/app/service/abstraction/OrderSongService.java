package spring.app.service.abstraction;

import spring.app.model.OrderSong;

import java.sql.Timestamp;
import java.util.List;

public interface OrderSongService {

    void addSongOrder(OrderSong songOrder);
    long getSongOrdersByCompanyIdAndPeriod(Long id, Long period);
    long getSongOrdersByCompanyIdAndTimeRange(Long id, String period);
    List<String> getOrdersSlicedByPeriod(Long companyId, String period);

    long countAll(Long id);
}
