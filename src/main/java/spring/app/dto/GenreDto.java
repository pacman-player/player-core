package spring.app.dto;

public class GenreDto {

    private Long id;
    private String name;
    private Boolean isApproved;

    public GenreDto() {
    }

    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDto(String name, Boolean isApproved) {
        this.name = name;
        this.isApproved = isApproved;
    }

    public GenreDto(Long id, String name, Boolean isApproved) {
        this.id = id;
        this.name = name;
        this.isApproved = isApproved;
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

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
