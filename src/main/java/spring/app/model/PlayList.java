package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "playlists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

//    private Long companyId;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = SongCompilation.class)
    @JoinTable(name = "song_compilation_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_compilation_id")})
    private Set<SongCompilation> songCompilation = new HashSet<>();

    public PlayList() {
    }

    public PlayList(String name, Set<SongCompilation> songCompilation) {
        this.name = name;
        this.songCompilation = songCompilation;
    }

//    public PlayList(Long id, Long companyId) {
//        this.id = id;
//        this.companyId = companyId;
//    }
//
//    public Long getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(Long companyId) {
//        this.companyId = companyId;
//    }

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

    public Set<SongCompilation> getSongCompilation() {
        return songCompilation;
    }

    public void setSongCompilation(Set<SongCompilation> songs) {
        this.songCompilation = songCompilation;
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