package spring.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SongDownloadRequestInfo {
    @Id
    @GeneratedValue
    private Long id;
    private String link;
    private String authorName;
    private String songName;

    public SongDownloadRequestInfo() {
    }

    public SongDownloadRequestInfo(String link, String authorName, String songName) {
        this.link = link;
        this.authorName = authorName;
        this.songName = songName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
