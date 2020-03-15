package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "genres")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //без этой аннотации LAZY не работало (по-моему не отображались песни)
public class Genre extends Bannable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    private Set<SongCompilation> songCompilation;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Author.class)
    @JoinTable(name = "author_on_genre",
            joinColumns = {@JoinColumn(name = "genre_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private Set<Author> authors;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "approved")
    private Boolean isApproved = false;

    /**
     * Вспомогательное поле, кокоторое используеться фронтом для корректного отображения данных.
     */
    @Transient
    private Boolean banned;

    public Genre(){}

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean isBanned() {
        return banned;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
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
