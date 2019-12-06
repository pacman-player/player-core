package spring.app.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="play_list")
public class PlayList {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Song.class)
    @JoinTable(name = "song_on_play_list",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "play_list_id")})
    private Set<Song> songs;

    public PlayList(){}

    public PlayList(String name, Set<Song> songs) {
        this.name = name;
        this.songs = songs;
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

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayList playList = (PlayList) o;

        if (id != null ? !id.equals(playList.id) : playList.id != null) return false;
        if (name != null ? !name.equals(playList.name) : playList.name != null) return false;
        return songs != null ? songs.equals(playList.songs) : playList.songs == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (songs != null ? songs.hashCode() : 0);
        return result;
    }
}
