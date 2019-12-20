package spring.app.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "genre")
    private Set<SongСompilation> songСompilation;

    public Genre(){}

    public Genre(String name) {
        this.name = name;
    }

    public Set<SongСompilation> getSongСompilation() {
        return songСompilation;
    }

    public void setSongСompilation(Set<SongСompilation> songСompilation) {
        this.songСompilation = songСompilation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (id != null ? !id.equals(genre.id) : genre.id != null) return false;
        return name != null ? name.equals(genre.name) : genre.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
