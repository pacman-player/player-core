package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.dao.abstraction.TagDao;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.SongDto;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.model.Tag;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongService;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongServiceImpl implements SongService {
    private final SongDao songDao;
    private final SongDtoDao songDtoDao;
    private final TagDao tagDao;
    private final SongCompilationService songCompilationService;

    @Autowired
    public SongServiceImpl(SongDao songDao, SongDtoDao songDtoDao, TagDao tagDao,
                           SongCompilationService songCompilationService) {
        this.songDao = songDao;
        this.songDtoDao = songDtoDao;
        this.tagDao = tagDao;
        this.songCompilationService = songCompilationService;
    }


    @Override
    public void addSong(Song song) {
        songDao.save(song);
    }



    @Override
    public void deleteSongById(Long id) {
        songDao.deleteById(id);
    }



    @Override
    public boolean isExist(String name) {
        return songDao.isExist(name);
    }


    @Override
    public Song getByName(String name) {
        return songDao.getByName(name);
    }

    //TODO: кандидат на удаление, не используется
//    @Override
//    public Song getByAuthorAndName(String author, String name) {
//        return songDao.getByAuthorAndName(author, name);
//    }

    @Override
    public Song getBySearchRequests(String author, String name) {
        return songDao.getBySearchRequests(author, name);
    }

    @Override
    public List<Song> getAllSongs() {
        return songDao.getAll();
    }

    @Override
    public List<SongDto> getAllSongsDto() {
        return songDtoDao.getAll();
    }

    @Override
    public List<Song> findSongsByNameContaining(String name) {
        return songDao.findByNameContaining(name);
    }

    @Override
    public Song getById(long songId) {
        return songDao.getById(songId);
    }

    @Override
    public List<Song> findSongsByGenreId(Long id) {
        return songDao.getAllWithGenreByGenreId(id);
    }

    @Override
    public List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return songDao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<Song> getAllSongInSongCompilation(Long id) {
        SongCompilation songCompilation = songCompilationService.getSongCompilationById(id);
        Set<Song> allSongSet = songCompilation.getSong();
        return new ArrayList<>(allSongSet);
    }

    @Override
    public List<Song> getAllApprovedSongs() {
        return songDao.getAllApproved();
    }
    @Override
    public void updateSong(Song song) {
        songDao.update(song);
    }

    @Override
    public Song getSongById(Long id) {
        return songDao.getById(id);
    }

    @Override
    public List<Song> getApprovedSongsPage(int pageNumber, int pageSize) {
        return songDao.getApprovedPage(pageNumber, pageSize);
    }

    @Override
    public int getLastApprovedSongsPageNumber(int pageSize) {
        return songDao.getLastApprovedPageNumber(pageSize);
    }

    @Override
    public Long getSongIdByAuthorAndName(String author, String name) {
        return songDao.getSongIdByAuthorAndName(author, name);
    }

    @Override
    public Long getAuthorIdBySongId(Long songId) {
        return songDao.getAuthorIdBySongId(songId);
    }

    @Override
    public void setTags(Song song, String tagString) {
        Set<String> stringTags = new HashSet<String>(Arrays.asList(tagString.split(",| |\t")));
        setTags(song, stringTags);
    }

    @Override
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
    public List<SongDto> listOfSongsByTag(String tag) {
       return songDtoDao.listOfSongsByTag(tag);
    }

    @Override
    public void deleteTagForSongs(List<Long> songIds, Long tagId) {
        int count = songDao.deleteTagForSongs(songIds, tagId);
        if (count != songIds.size()) {
            throw new IllegalArgumentException("Incorrect song ids: can't delete tag (id = " + tagId + ") for all songs (ids = {"
                                            + songIds + "]).");
        }
    }
}