package spring.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class SongThatPlayNow {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @MapsId("companyId")
    @ManyToOne
    private Company company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @EmbeddedId
    private SongThatPlayNowId songThatPlayNowId;


    public SongThatPlayNowId getSongThatPlayNowId() {
        return songThatPlayNowId;
    }

    public void setSongThatPlayNowId(SongThatPlayNowId songThatPlayNowId) {
        this.songThatPlayNowId = songThatPlayNowId;
    }


}
