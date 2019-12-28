package spring.app.dto;

public class SearchSongDTO {
    private Long id;
    private String authorName;
    private String songName;

    public SearchSongDTO(Long id, String authorName, String songName) {
        this.id = id;
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
}
