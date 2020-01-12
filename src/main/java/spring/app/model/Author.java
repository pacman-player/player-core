package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author extends Bannable{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(targetEntity = Genre.class)
    @JoinTable(name = "author_on_genre",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> authorGenres = new HashSet<>();

    /**
     * Поле, которое заполняеться вручную.
     * Нужно для определение находится ли автор в "бане" у компании.
     */
    @Transient
    private Boolean banned;

    public Author(){}

    public Author(String name) {
        this.name = name;
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
        return authorGenres;
    }

    public void setAuthorGenres(Set<Genre> authorGenres) {
        this.authorGenres = authorGenres;
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
}