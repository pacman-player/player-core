package spring.app.model;

import javax.persistence.*;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    private Long id;

    private String name;

    public Genre(){}

    public Genre(String name) {
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
}
