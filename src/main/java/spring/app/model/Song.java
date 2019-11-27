package spring.app.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Song {
    @Id
    private Long Id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Author.class)
    private Author author;

    public Song() {
    }

    @OneToMany(mappedBy = "song")
    private Set<SongQueue> song;

    public void setId(Long id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }
}
