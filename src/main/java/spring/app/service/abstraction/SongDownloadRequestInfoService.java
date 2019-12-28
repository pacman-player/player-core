package spring.app.service.abstraction;

import spring.app.model.SongDownloadRequestInfo;

import java.util.List;

public interface SongDownloadRequestInfoService {
void addSongDownloadRequestInfo(SongDownloadRequestInfo songDownloadRequestInfo);
    public List<SongDownloadRequestInfo> getBySongName(String songName);
    public List<SongDownloadRequestInfo> getByAuthorName(String authorName);
    public SongDownloadRequestInfo getById(String id);
    public void deleteById(String id);
    public void deleteAll();
}
