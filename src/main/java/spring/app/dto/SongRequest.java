package spring.app.dto;

public class SongRequest {
    private Long chatId;
    private String authorName;
    private String songName;
    private Long songId;
    private Long companyId;

    public SongRequest() {
    }

    public SongRequest(Long chatId, String authorName, String songName, Long songId, Long companyId) {
        this.chatId = chatId;
        this.authorName = authorName;
        this.songName = songName;
        this.songId = songId;
        this.companyId = companyId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
