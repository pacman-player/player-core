package spring.app.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "palyList")
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public PlayList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayList playList = (PlayList) o;
        return Objects.equals(id, playList.id) &&
                Objects.equals(name, playList.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
