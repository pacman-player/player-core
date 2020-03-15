package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "song_compilations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//без этой аннотации LAZY не работало (по-моему не отображались песни)
public class SongCompilation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @JsonIgnore
    @ManyToMany(targetEntity = Song.class)
    @JoinTable(name = "song_compilation_on_song",
            joinColumns = {@JoinColumn(name = "song_compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> song = new HashSet<>();

    public SongCompilation() {
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
        SongCompilation that = (SongCompilation) o;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "SongCompilation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", song=" + song +
                ", cover=" + cover +
                '}';
    }
}
