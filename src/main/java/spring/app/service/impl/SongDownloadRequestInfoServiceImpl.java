package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.SongDownloadRequestInfoServiceDao;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.SongDownloadRequestInfoService;

import java.util.List;

@Service
public class SongDownloadRequestInfoServiceImpl implements SongDownloadRequestInfoService {
    private SongDownloadRequestInfoServiceDao songDownloadRequestInfoServiceDao;

    @Autowired
    public SongDownloadRequestInfoServiceImpl(SongDownloadRequestInfoServiceDao songDownloadRequestInfoServiceDao) {
        this.songDownloadRequestInfoServiceDao = songDownloadRequestInfoServiceDao;
    }

    @Override
    public void addSongDownloadRequestInfo(SongDownloadRequestInfo songDownloadRequestInfo) {
        songDownloadRequestInfoServiceDao.save(songDownloadRequestInfo);
    }

    @Override
    public List<SongDownloadRequestInfo> getBySongName(String songName) {
        return songDownloadRequestInfoServiceDao.getBySongName(songName);
    }

    @Override
    public List<SongDownloadRequestInfo> getByAuthorName(String authorName) {
        return songDownloadRequestInfoServiceDao.getByAuthorName(authorName);
    }

    @Override
    public SongDownloadRequestInfo getById(String id) {
        return songDownloadRequestInfoServiceDao.getById(id);
    }

    @Override
    public void deleteById(String id) {
        songDownloadRequestInfoServiceDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        songDownloadRequestInfoServiceDao.deleteAll();
    }
}
