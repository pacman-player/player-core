package spring.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "song_compilation")
public class SongCompilation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Song.class)
    @JoinTable(name = "song_compilation_on_song",
            joinColumns = {@JoinColumn(name = "song_compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> song = new HashSet<>();

    //add Igor Khilkevich
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = PlayList.class)
//    @JoinTable(name = "song_compilation_on_play_list",
//            joinColumns = {@JoinColumn(name = "song_compilation_id")},
//            inverseJoinColumns = {@JoinColumn(name = "play_list_id")})
    private List<PlayList> playLists;

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

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<PlayList> playLists) {
        this.playLists = playLists;
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
}
