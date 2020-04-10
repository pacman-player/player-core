package core.app.service.entity;

import java.nio.file.Path;

public class Track {
    private final String author;
    private final String song;
    private final String fullTrackName;
    private final byte[] track;
    private Path path;


    public Track(String author, String song, String fullTrackName, byte[] track, Path path) {
        this.author = author;
        this.song = song;
        this.fullTrackName = fullTrackName;
        this.track = track;
        this.path = path;

    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
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
