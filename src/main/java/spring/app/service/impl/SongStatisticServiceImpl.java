package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongStatisticDao;
import spring.app.model.SongStatistic;
import spring.app.service.abstraction.SongStatisticService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class SongStatisticServiceImpl extends AbstractServiceImpl<Long, SongStatistic, SongStatisticDao> implements SongStatisticService {


    public SongStatisticServiceImpl(SongStatisticDao dao) {
        super(dao);
    }

    @Override
    public Long getOrderCountBySongName(String songName) {
        return dao.getOrderCountBySongName(songName);
    }

    @Override
    public List<SongStatistic> getSortedTopList(int songsCount, String period) {
        switch (period) {
            case "day": return dao.getSortedTopListForDay(songsCount);
            case "week": return dao.getSortedTopListForRange(songsCount, Date.valueOf(LocalDate.now().minusWeeks(1)));
            case "month": return dao.getSortedTopListForRange(songsCount, Date.valueOf(LocalDate.now().minusMonths(1)));
            case "year": return dao.getSortedTopListForRange(songsCount, Date.valueOf(LocalDate.now().minusYears(1)));
        }
        return dao.getSortedTopListForDay(songsCount);
    }

    @Override
    public void save(SongStatistic entity) {

    }

    @Override
    @Transactional
    public void saveOrUpdate(SongStatistic entity) {
        SongStatistic songStatistic = dao.getSongStatisticByNameAndDate(entity.getSongName(), entity.getOrderDate());
        if (songStatistic == null) {
            entity.setOrderCount(Long.valueOf(1));
            dao.save(entity);
        } else {
            songStatistic.setOrderCount(songStatistic.getOrderCount() + 1);
            dao.update(songStatistic);
        }
    }

    @Override
    public SongStatistic getById(Long id) {
        return null;
    }

    @Override
    public void update(SongStatistic entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean isExistById(Long id) {
        return false;
    }

    @Override
    public List<SongStatistic> findByNameContaining(String param) {
        return null;
    }

    @Override
    public List<SongStatistic> getAll() {
        return dao.getAll();
    }

    @Override
    public SongStatistic getRecordById(Long id) {
        return null;
    }
}
