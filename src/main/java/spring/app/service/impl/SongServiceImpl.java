package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.SongDto;
import spring.app.dto.SongDtoTop;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongFileService;
import spring.app.service.abstraction.SongService;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SongServiceImpl extends AbstractServiceImpl<Long, Song, SongDao> implements SongService {


    private final SongDtoDao songDtoDao;
    private final SongCompilationService songCompilationService;
    private final SongFileService songFileService;

    @Autowired
    public SongServiceImpl(SongDao dao, SongDtoDao songDtoDao, SongCompilationService songCompilationService, SongFileService songFileService) {
        super(dao);
        this.songDtoDao = songDtoDao;
        this.songCompilationService = songCompilationService;
        this.songFileService = songFileService;
    }

    @Override
    public boolean isExist(String name) {
        return dao.isExist(name);
    }


    @Override
    public Song getByName(String name) {
        return dao.getByName(name);
    }

    //TODO: кандидат на удаление, не используется
//    @Override
//    public Song getByAuthorAndName(String author, String name) {
//        return songDao.getByAuthorAndName(author, name);
//    }

    @Override
    public Song getBySearchRequests(String author, String name) {
        return dao.getBySearchRequests(author, name);
    }


    @Override
    public List<SongDto> getAllSongsDto() {
        return songDtoDao.getAll();
    }


    @Override
    public List<Song> findSongsByGenreId(Long id) {
        return dao.getAllWithGenreByGenreId(id);
    }

    @Override
    public List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return dao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<Song> getAllSongInSongCompilation(Long id) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(id);
        Set<Song> allSongSet = songCompilation.getSong();
        return new ArrayList<>(allSongSet);
    }

    @Override
    public List<Song> getAllApprovedSongs() {
        return dao.getAllApproved();
    }


    @Override
    public List<Song> getApprovedSongsPage(int pageNumber, int pageSize) {
        return dao.getApprovedPage(pageNumber, pageSize);
    }

    @Override
    public int getLastApprovedSongsPageNumber(int pageSize) {
        return dao.getLastApprovedPageNumber(pageSize);
    }

    @Override
    public Long getSongIdByAuthorAndName(String author, String name) {
        return dao.getSongIdByAuthorAndName(author, name);
    }

    @Override
    public Long getAuthorIdBySongId(Long songId) {
        return dao.getAuthorIdBySongId(songId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Song song = dao.getById(id);
        dao.deleteById(id);
        // удаление песни физически
        songFileService.deleteSongFile(song);
    }

    @Override
    public List<SongDtoTop> getTopSongsByNumberOfList(int numbOfList) {
        Timestamp startTime = getBeginTime(numbOfList);
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        System.out.println("11111111111111111" + startTime + "    " + endTime);
        List<SongDtoTop> songDtoTops = songDtoDao.getTopSongsByNumberOfList(startTime, endTime);
        Collections.sort(songDtoTops);
        return songDtoTops;
    }

    @Override
    public SongDtoTop getSongDtoTopWithPoint(int numbOfList, Long idSong) {
        Timestamp startTime = getBeginTime(numbOfList);
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        SongDtoTop songDtoTop = songDtoDao.getSongDtoTopWithPoint(startTime, endTime, idSong);
        return songDtoTop;
    }

    private Timestamp getBeginTime(int numbOfList) {
        int begin = 0;
        switch (numbOfList) {
            case 1:
                begin = 1;
                break;
            case 2:
                begin = 7;
                break;
            case 3:
                begin = 30;
                break;
            case 4:
                begin = 365;
                break;
            default:
                throw new IllegalArgumentException(); //выборка может быть за день, неделю, месяц или год
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -begin);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date beginDay = cal.getTime();
        Timestamp beginTimestamp = new Timestamp(beginDay.getTime());
        return beginTimestamp;
    }
}