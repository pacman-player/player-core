package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.model.OrderSong;
import spring.app.service.abstraction.OrderSongService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class OrderSongServiceImpl implements OrderSongService {


    private OrderSongDao orderSongDao;

    @Autowired
    public void setOrderSongDao(OrderSongDao orderSongDao) {
        this.orderSongDao = orderSongDao;
    }

    @Override
    public void addSongOrder(OrderSong songOrder) {
        orderSongDao.save(songOrder);
    }


    @Override
    public long getSongOrdersByCompanyIdAndPeriod(Long id, Long period) {
        return orderSongDao.getSongOrdersByCompanyIdAndPeriod(id, new Timestamp(System.currentTimeMillis() - period * 24 * 60 * 60 * 1000));
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
        long result = orderSongDao.getSongOrdersByCompanyIdAndTimeRange(id, startOfTheDayToday, new Timestamp(System.currentTimeMillis()));
        if (period.equals("yesterday")) {
            result = orderSongDao.getSongOrdersByCompanyIdAndTimeRange(id, startOfTheDayYesterday, startOfTheDayToday);
        }
        return result;
    }

    @Override
    public long countAll(Long companyId) {
        return orderSongDao.countAll(companyId);
    }


    @Override
    public void bulkRemoveByCompany(Long companyId) {
        orderSongDao.bulkRemoveOrderSongByCompany(companyId);
    }
}
