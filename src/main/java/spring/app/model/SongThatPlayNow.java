package spring.app.model;

import javax.persistence.*;

@Entity
public class SongThatPlayNow {
    @EmbeddedId
    private SongThatPlayNowId songThatPlayNowId;

    @ManyToOne
    @MapsId("company_id")
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @MapsId("song_id")
    @JoinColumn(name = "song_id")
    private Song song;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public SongThatPlayNowId getSongThatPlayNowId() {
        return songThatPlayNowId;
    }

    public void setSongThatPlayNowId(SongThatPlayNowId songThatPlayNowId) {
        this.songThatPlayNowId = songThatPlayNowId;
    }
}
