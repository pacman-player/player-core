package spring.app.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Author.class)
    private Author author;

    public Song() {
    }

    @OneToMany(mappedBy = "song")
    private Set<SongQueue> song;

    public void setId(Long id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return Id;
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

        Song song1 = (Song) o;

        if (Id != null ? !Id.equals(song1.Id) : song1.Id != null) return false;
        if (name != null ? !name.equals(song1.name) : song1.name != null) return false;
        if (author != null ? !author.equals(song1.author) : song1.author != null) return false;
        return song != null ? song.equals(song1.song) : song1.song == null;
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (song != null ? song.hashCode() : 0);
        return result;
    }

}
