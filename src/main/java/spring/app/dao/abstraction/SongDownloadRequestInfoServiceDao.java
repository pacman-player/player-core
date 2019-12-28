package spring.app.dao.abstraction;

import spring.app.model.SongDownloadRequestInfo;

import java.util.List;

public interface SongDownloadRequestInfoServiceDao  extends GenericDao<Long, SongDownloadRequestInfo>  {
    public void save(SongDownloadRequestInfo songDownloadRequestInfo);
    public List<SongDownloadRequestInfo> getBySongName(String songName);
    public List<SongDownloadRequestInfo> getByAuthorName(String authorName);
    public SongDownloadRequestInfo getById(String id);
    public void deleteById(String id);
    public void deleteAll();
}
