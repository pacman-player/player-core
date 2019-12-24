package spring.app.dto;

public class GenreDto {
    private String id;
    private String name;

    public Long getId() {
        Long id = Long.parseLong(this.id);
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
