package core.app.service.abstraction;

import core.app.model.OrderSong;

public interface OrderSongService {

    void addSongOrder(OrderSong songOrder);
    long getSongOrdersByCompanyIdAndPeriod(Long id, Long period);
    long getSongOrdersByCompanyIdAndTimeRange(Long id, String period);

    long countAll(Long id);

    void bulkRemoveByCompany(Long userId);
}
