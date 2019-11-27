package spring.app.model;


import javax.persistence.*;
import java.util.Set;

@Entity

public class PlayList {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = PlayList.class)
    @JoinTable(name = "song_play_list",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "play_list_id")})
    private Set<Song> songs;

    public PlayList(){}


    public PlayList(String name, Set<Song> songs) {
        this.name = name;
        this.songs = songs;
    }
}
