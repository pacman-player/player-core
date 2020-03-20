package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//объекты загружаются лениво, и сериализация происходит до того как они будут загружены полность.
// Без этой аннотаци не отображаются песни на странице в админке
public class Author extends Bannable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    //    @Fetch(FetchMode.SUBSELECT) //для решения возможной проблемы N+1 только у ManyToMany,
    //    закомментил тк все норм работает
    @ManyToMany(targetEntity = Genre.class)
    @JoinTable(name = "author_on_genre",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres = new HashSet<>();

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

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(Long id, String name, Set<Genre> genres, Timestamp createdAt, Timestamp updatedAt, Boolean isApproved) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isApproved = isApproved;
    }

    public Author(String name, Set<Genre> genres, Timestamp createdAt, Timestamp updatedAt, Boolean isApproved) {
        this.name = name;
        this.genres = genres;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isApproved = isApproved;
    }

    public Author(Long id, String name, Set<Genre> genres, Boolean isApproved) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.isApproved = isApproved;
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

    public Set<Genre> getAuthorGenres() {
        return genres;
    }

    public void setAuthorGenres(Set<Genre> genres) {
        this.genres = genres;
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

    @Override
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean isBannedBy(Company company) {
        return company.getBannedAuthor().contains(this);
    }

    public Boolean getBanned() {
        return banned;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
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
        Author author = (Author) o;
        return id.equals(author.id) &&
                name.equals(author.name);
    }

    @Override
    public String toString() {
        return getName();
    }
}