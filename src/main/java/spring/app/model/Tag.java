package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(name = "tags")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tag {

    static final Pattern NAME_PATTERN = Pattern.compile("[A-Za-zА-Яа-я0-9]+");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Song.class)
    @ManyToMany(targetEntity = Song.class)
    @JoinTable(name = "tag_on_song",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> songs = new HashSet<>();

    public Tag() {
    }

    public Tag(String name) {
        setName(name);
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
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Illegal tag name: " + name);
        }
        this.name = name;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return getName();
    }
}
