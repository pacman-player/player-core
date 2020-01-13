package spring.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "song")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Song extends Bannable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "song")
    private Set<SongQueue> song;

    /**
     * Вспомогательное поле, кокоторое используеться фронтом для корректного отображения данных.
     */
    @Transient
    private Boolean banned;

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
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean isBannedBy(Company company) {
        return company.getBannedSong().contains(this);
    }

    public Boolean getBanned() {
        return banned;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song1 = (Song) o;
        return id.equals(song1.id) &&
                name.equals(song1.name);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", song=" + song +
                '}';
    }
}
