package spring.app.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="orderSong")
public class OrderSong {
    @Id
    @GeneratedValue
    private Long id;

    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(columnDefinition = "song_id")
    private Song song;

    public OrderSong() {
    }

    public OrderSong(Company company, Timestamp timestamp) {
        this.company = company;
        this.timestamp = timestamp;
    }

    public OrderSong(Company company, Timestamp timestamp, Song song) {
        this.company = company;
        this.timestamp = timestamp;
        this.song = song;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

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
}
