package spring.app.dto;


public class AuthorDto {
    private Long id;
    private String name;
    private String genres;

    public AuthorDto() {
    }

    public AuthorDto(String name, String genres) {
        this.name = name;
        this.genres = genres;
    }

    public AuthorDto(Long id, String name, String genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
