package spring.app.service.entity;

public class Track {
    private final int id;
    private final String author;
    private final String track;
    private final String link;

    public Track(int id, String author, String track, String link) {
        this.id = id;
        this.author = author;
        this.track = track;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTrack() {
        return track;
    }

    public String getLink() {
        return link;
    }
}
