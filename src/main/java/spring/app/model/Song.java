package spring.app.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "song")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Author.class)
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Genre.class)
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "song")
    private Set<SongQueue> song;

    public Song() {
    }

    public Song(Long id, Author author, Genre genre, Set<SongQueue> song) {
        this.id = id;
        this.author = author;
        this.genre = genre;
        this.song = song;
    }

    public Song(String name, Author author, Genre genre, Set<SongQueue> song) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.song = song;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Song(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<SongQueue> getSong() {
        return song;
    }

    public void setSong(Set<SongQueue> song) {
        this.song = song;
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
