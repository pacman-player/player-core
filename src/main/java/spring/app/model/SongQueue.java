package spring.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SongQueue {
    @Id
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
