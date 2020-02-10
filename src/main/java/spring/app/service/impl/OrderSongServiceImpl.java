package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.OrderSong;
import spring.app.repository.OrderSongRepository;
import spring.app.service.abstraction.OrderSongService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class OrderSongServiceImpl implements OrderSongService {


    private OrderSongRepository orderSongRepository;

    @Autowired
    public void setOrderSongRepository(OrderSongRepository orderSongRepository) {
        this.orderSongRepository = orderSongRepository;
    }

    @Override
    public void addSongOrder(OrderSong songOrder) {
    orderSongRepository.save(songOrder);
    }

    @Override
    public void deleteAllSongOrders() {
        orderSongRepository.deleteAll();
    }

    @Override
    public long getSongOrdersByCompanyIdAndPeriod(Long id, Long period) {
       return orderSongRepository.countByCompany_IdAndTimestampAfter(id, new Timestamp(System.currentTimeMillis() - period * 24 * 60 * 60 * 1000));
    }

    @Override
    public long getSongOrdersByCompanyIdAndTimeRange(Long id, String period) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date today = cal.getTime();
        Timestamp startOfTheDayToday = new Timestamp(today.getTime());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date yesterday = cal.getTime();
        Timestamp startOfTheDayYesterday = new Timestamp(yesterday.getTime());
        Long result = orderSongRepository.countByCompanyIdAndTimestampBetween(id, startOfTheDayToday, new Timestamp(System.currentTimeMillis()));
        if (period.equals("yesterday")) {
            result = orderSongRepository.countByCompanyIdAndTimestampBetween(id, startOfTheDayYesterday, startOfTheDayToday);
        }
        return result;
    }

    @Override
    public long countAll(Long companyId) {
        return orderSongRepository.countAllByCompanyId(companyId);
    }
}
