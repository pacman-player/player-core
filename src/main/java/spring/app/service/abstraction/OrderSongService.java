package spring.app.service.abstraction;

import spring.app.dto.SongDto;
import spring.app.model.OrderSong;

import java.util.List;

public interface OrderSongService extends GenericService<Long, OrderSong> {

    long getSongOrdersByCompanyIdAndPeriod(Long id, Long period);

    long getSongOrdersByCompanyIdAndTimeRange(Long id, String period);

    long countAll(Long id);

    void bulkRemoveByCompany(Long userId);

}
