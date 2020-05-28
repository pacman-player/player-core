package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CounterDao;
import spring.app.dao.abstraction.SongDao;
import spring.app.dao.abstraction.TagDao;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.BotSongDto;
import spring.app.dto.SongDto;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.model.Tag;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongFileService;
import spring.app.service.abstraction.SongService;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl extends AbstractServiceImpl<Long, Song, SongDao> implements SongService {

    private final SongDtoDao songDtoDao;
    private final TagDao tagDao;
    private final SongCompilationService songCompilationService;
    private final SongFileService songFileService;
    private final CounterDao counterDao;

    @Autowired
    public SongServiceImpl(SongDao dao, SongDtoDao songDtoDao, TagDao tagDao, CounterDao counterDao,
                           SongCompilationService songCompilationService, SongFileService songFileService) {
        super(dao);
        this.songDtoDao = songDtoDao;
        this.tagDao = tagDao;
        this.songCompilationService = songCompilationService;
        this.songFileService = songFileService;
        this.counterDao = counterDao;
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
    public List<BotSongDto> getBySearchRequests(String author, String name) {
        return songDtoDao.getBySearchRequests(author, name);
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
    public List<SongDto> getAllSongsByCompilationId(Long id) {
        return songCompilationService.getSongCompilationContentById(id);
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
    public void resetSongCounter(long songId) {
        SongDto dto = songDtoDao.getById(songId);
        String trackName = dto.getAuthorName().toUpperCase() + " - " + dto.getName().toUpperCase();
        counterDao.restart(trackName);
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
    @Transactional
    public void setTags(Song song, String tagString) {
        Set<String> stringTags = new HashSet<String>(Arrays.asList(tagString.split(",| |\t")));
        setTags(song, stringTags);
    }

    @Override
    @Transactional
    public void setTags(Song song, Set<String> stringTags) {
        Set<String> tags = stringTags.stream().map(String::toLowerCase).collect(Collectors.toSet());
        Set<Tag> foundTags = tagDao.getByNames(tags);
        List<Tag> newTags = Collections.EMPTY_LIST;
        if (foundTags.size() < tags.size()) {
            for (Tag ft : foundTags) {
                tags.remove(ft.getName());
            }
            newTags = tags.stream().map(t -> new Tag(t)).collect(Collectors.toList());
            tagDao.saveBatch(newTags);
        }
        foundTags.addAll(newTags);
        song.setTags(foundTags);
    }

    @Override
    public List<SongDto> listOfSongsByName(String name) {
        return songDtoDao.listOfSongsByName(name);
    }

    @Override
    public List<SongDto> listOfSongsByTag(String tag) {
        return songDtoDao.listOfSongsByTag(tag);
    }

    @Override
    @Transactional
    public void deleteTagForSongs(List<Long> songIds, Long tagId) {
        int count = dao.deleteTagForSongs(songIds, tagId);
        if (count != songIds.size()) {
            throw new IllegalArgumentException("Incorrect song ids: can't delete tag (id = " + tagId + ") for all songs (ids = {"
                    + songIds + "]).");
        }
    }
}