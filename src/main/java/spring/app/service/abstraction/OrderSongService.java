package spring.app.service.abstraction;

import spring.app.model.OrderSong;

public interface OrderSongService extends GenericService<Long, OrderSong> {

    long getSongOrdersByCompanyIdAndPeriod(Long id, Long period);

    long getSongOrdersByCompanyIdAndTimeRange(Long id, String period);

    long countAll(Long id);

    void bulkRemoveByCompany(Long userId);
}
