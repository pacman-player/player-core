package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.model.OrderSong;
import spring.app.service.abstraction.OrderSongService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public List<String> getOrdersSlicedByPeriod(Long companyId, String period) {
        List<String> result = new ArrayList<>();
        if (period.equals("year")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -11);
            cal.set(Calendar.DATE, 0);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            String[] monthNames = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
            int incr = 1;
            for (int i = 0; i < 12; i++) {
                Timestamp start = new Timestamp(cal.getTime().getTime());
                cal.add(Calendar.MONTH, incr);
                String monthName = monthNames[cal.get(Calendar.MONTH)] + "." + cal.get(Calendar.YEAR);
                Timestamp end = new Timestamp(cal.getTime().getTime());
                long ordersInMonths = orderSongDao.getSongOrdersByCompanyIdAndTimeRange(companyId, start, end);
                result.add(monthName + ":" + ordersInMonths);
            }
            System.out.println(result);
        } else if (period.equals("month")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -31);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            int incr = 1;
            for (int i = 0; i < 31; i++) {
                Timestamp start = new Timestamp(cal.getTime().getTime());
                cal.add(Calendar.DATE, incr);
                int date = cal.get(Calendar.DATE);
                System.out.println(date);
                Timestamp end = new Timestamp(cal.getTime().getTime());
                long ordersInDay = orderSongDao.getSongOrdersByCompanyIdAndTimeRange(companyId, start, end);
                result.add(date + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR) + ":" + ordersInDay);
            }
            System.out.println(result);
        }


        return result;
    }

    @Override
    public long countAll(Long companyId) {
        return orderSongDao.countAll(companyId);
    }
}
