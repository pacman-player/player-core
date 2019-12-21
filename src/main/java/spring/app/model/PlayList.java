package spring.app.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="play_list")
public class PlayList {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(targetEntity = SongСompilation.class)
    @JoinTable(name = "songСompilation_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "songСompilation_id")})
    private Set<SongСompilation> songСompilation = new HashSet<>();

    public PlayList(){}

    public PlayList(String name, Set<SongСompilation> songСompilation) {
        this.name = name;
        this.songСompilation = songСompilation;
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

    public Set<SongСompilation> getSongСompilation() {
        return songСompilation;
    }

    public void setSongСompilation(Set<SongСompilation> songs) {
        this.songСompilation = songСompilation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayList playList = (PlayList) o;

        if (id != null ? !id.equals(playList.id) : playList.id != null) return false;
        return name != null ? !name.equals(playList.name) : playList.name != null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
