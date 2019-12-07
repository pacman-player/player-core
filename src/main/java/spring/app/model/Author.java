package spring.app.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(targetEntity = Genre.class)
    @JoinTable(name = "author_on_genre",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> authorGenres;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != null ? !id.equals(author.id) : author.id != null) return false;
        return name != null ? !name.equals(author.name) : author.name != null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
