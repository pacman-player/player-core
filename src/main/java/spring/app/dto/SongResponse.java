package spring.app.dto;

public class SongResponse {

    private Long chatId;
    private Long songId;
    private byte[] track;
    private String trackName;
    private Long position;
    private boolean isBanned;

    public SongResponse(Long chatId, Long songId, byte[] track, String trackName, Long position, boolean isBanned) {
        this.chatId = chatId;
        this.songId = songId;
        this.track = track;
        this.trackName = trackName;
        this.position = position;
        this.isBanned = isBanned;
    }

    public SongResponse() {
    }

    public SongResponse(Long chatId, boolean isBanned) {
        this.chatId = chatId;
        this.isBanned = isBanned;
    }

    public SongResponse(Long chatId, Long songId, boolean isBanned) {
        this.chatId = chatId;
        this.songId = songId;
        this.isBanned = isBanned;
    }

    public SongResponse(Long chatId, Long songId, byte[] track, String trackName) {
        this.chatId = chatId;
        this.songId = songId;
        this.track = track;
        this.trackName = trackName;
    }

    public SongResponse(Long chatId, Long songId, byte[] track, String trackName, Long position) {
        this.chatId = chatId;
        this.songId = songId;
        this.track = track;
        this.trackName = trackName;
        this.position = position;
    }



    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public byte[] getTrack() {
        return track;
    }

    public void setTrack(byte[] track) {
        this.track = track;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
