package spring.app.model;

import javax.persistence.*;

@Entity
public class SongQueue {
    @Id
    @GeneratedValue
    private Long id;
    private Long position;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public SongQueue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
