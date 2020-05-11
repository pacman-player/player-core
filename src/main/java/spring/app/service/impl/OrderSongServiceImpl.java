package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.dto.SongDto;
import spring.app.model.Company;
import spring.app.model.OrderSong;
import spring.app.service.abstraction.OrderSongService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderSongServiceImpl extends AbstractServiceImpl<Long, OrderSong, OrderSongDao> implements OrderSongService {

    protected OrderSongServiceImpl(OrderSongDao dao) {
        super(dao);
    }

    @Autowired
    private CompanyServiceImpl companyService;

    @Override
    public long getSongOrdersByCompanyIdAndPeriod(Long id, Long period) {
        return dao.getSongOrdersByCompanyIdAndPeriod(id, new Timestamp(System.currentTimeMillis() - period * 24 * 60 * 60 * 1000));
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
        long result = dao.getSongOrdersByCompanyIdAndTimeRange(id, startOfTheDayToday, new Timestamp(System.currentTimeMillis()));
        if (period.equals("yesterday")) {
            result = dao.getSongOrdersByCompanyIdAndTimeRange(id, startOfTheDayYesterday, startOfTheDayToday);
        }
        return result;
    }

    @Override
    public long countAll(Long companyId) {
        return dao.countAll(companyId);
    }


    @Override
    public void bulkRemoveByCompany(Long companyId) {
        dao.bulkRemoveOrderSongByCompany(companyId);
    }


}
