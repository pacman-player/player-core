package spring.app.repository;

import org.springframework.data.repository.CrudRepository;
import spring.app.model.OrderSong;

import java.sql.Timestamp;

public interface OrderSongRepository extends CrudRepository<OrderSong, Long> {

    Long countByCompany_IdAndTimestampAfter(Long id, Timestamp timestamp);
    Long countAllByCompanyId(Long id);
    Long countByCompanyIdAndTimestampBetween(Long id, Timestamp start, Timestamp end);
}
