package spring.app.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="order_song")
public class OrderSong {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public OrderSong() {
    }

    public OrderSong(Company company, Timestamp timestamp) {
        this.company = company;
        this.timestamp = timestamp;
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
}
