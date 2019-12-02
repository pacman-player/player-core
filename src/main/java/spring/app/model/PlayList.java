package spring.app.model;


import javax.persistence.*;
import java.util.Set;

@Entity

public class PlayList {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Song.class)
    @JoinTable(name = "song_play_list",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "play_list_id")})
    private Set<Song> songs;

    public PlayList(){}


    public PlayList(String name, Set<Song> songs) {
        this.name = name;
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
