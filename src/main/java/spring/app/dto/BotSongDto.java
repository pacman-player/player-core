package spring.app.dto;

public class BotSongDto {

    private Long songId;
    private String trackName;

    public BotSongDto() {
    }

    public BotSongDto(Long songId, String trackName) {
        this.songId = songId;
        this.trackName = trackName;
    }

    public BotSongDto(Long songId, String songName, String authorName) {
        this.songId = songId;
        this.trackName = songName + " - " + authorName;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
}
