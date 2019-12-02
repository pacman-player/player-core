package spring.app.model;



import javax.persistence.*;
import java.util.Set;

@Entity
public class OrgType {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Genre.class)
    @JoinTable(name = "org_type_on_related_genre",
            joinColumns = {@JoinColumn(name = "org_type_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres;

    public OrgType(String name) {
        this.name = name;
    }

    public OrgType(){}

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

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Genre> getGenres() {


        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgType orgType = (OrgType) o;

        if (id != null ? !id.equals(orgType.id) : orgType.id != null) return false;
        if (name != null ? !name.equals(orgType.name) : orgType.name != null) return false;
        return genres != null ? genres.equals(orgType.genres) : orgType.genres == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }
}

