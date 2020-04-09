package spring.app.service.entity;

public class Track {
    private final String author;
    private final String song;
    private final String fullTrackName;
    private final byte[] track;

    public Track(String author, String song, String fullTrackName, byte[] track) {
        this.author = author;
        this.song = song;
        this.fullTrackName = fullTrackName;
        this.track = track;
    }

    public String getAuthor() {
        return author;
    }

    public String getSong() {
        return song;
    }

    public String getFullTrackName() {
        return fullTrackName;
    }

    public byte[] getTrack() {
        return track;
    }
}
