package spring.app.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
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
}
