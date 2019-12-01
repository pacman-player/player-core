package spring.app.model;

import javax.persistence.*;

@Entity
public class SongQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongQueue songQueue = (SongQueue) o;

        if (id != null ? !id.equals(songQueue.id) : songQueue.id != null) return false;
        if (position != null ? !position.equals(songQueue.position) : songQueue.position != null) return false;
        if (song != null ? !song.equals(songQueue.song) : songQueue.song != null) return false;
        return company != null ? company.equals(songQueue.company) : songQueue.company == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (song != null ? song.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }
}
