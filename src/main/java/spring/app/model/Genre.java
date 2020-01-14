package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //без этой аннотации LAZY не работало (по-моему не отображались песни)
public class Genre extends Bannable{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    private Set<SongCompilation> songCompilation;

    /**
     * Вспомогательное поле, кокоторое используеться фронтом для корректного отображения данных.
     */
    @Transient
    private Boolean banned;

    public Genre(){}

    public Genre(String name) {
        this.name = name;
    }

    public Set<SongCompilation> getSongCompilation() {
        return songCompilation;
    }

    public void setSongCompilation(Set<SongCompilation> songCompilation) {
        this.songCompilation = songCompilation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean isBanned() {
        return banned;
    }

    @Override
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean isBannedBy(Company company) {
        return company.getBannedGenres().contains(this);
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
        Genre genre = (Genre) o;
        return id.equals(genre.id) &&
                name.equals(genre.name);
    }
}
