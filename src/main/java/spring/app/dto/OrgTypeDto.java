package spring.app.dto;

import java.util.List;
import java.util.Objects;

public class OrgTypeDto {

    private Long id;
    private String name;
    private boolean isDefault;
    private List<String> genres;

    public OrgTypeDto() {}

    public OrgTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public OrgTypeDto(Long id, String name, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
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
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "OrgTypeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDefault=" + isDefault +
                ", genres=" + genres +
                '}';
    }
}

