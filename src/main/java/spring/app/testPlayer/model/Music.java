package spring.app.testPlayer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "musics")
public class Music {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Music() {
    }

    public Music(String name) {
        this.name = name;
    }

    public Music(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
