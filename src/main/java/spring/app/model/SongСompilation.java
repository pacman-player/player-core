package spring.app.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "songСompilation")
public class SongСompilation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Song.class)
    @JoinTable(name = "songСompilation_on_song",
            joinColumns = {@JoinColumn(name = "songСompilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> song;

    public SongСompilation() {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Song> getSong() {
        return song;
    }

    public void setSong(Set<Song> song) {
        this.song = song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongСompilation that = (SongСompilation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
}
