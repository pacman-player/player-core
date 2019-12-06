package spring.app.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Author.class)
    private Author author;

    @OneToMany(mappedBy = "song")
    private Set<SongQueue> song;

    public Song() {
    }

    public Song(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SongQueue> getSong() {
        return song;
    }

    public void setSong(Set<SongQueue> song) {
        this.song = song;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (id != null ? !id.equals(song.id) : song.id != null) return false;
        return name != null ? !name.equals(song.name) : song.name != null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
