package spring.app.dto;


import spring.app.model.Author;
import spring.app.model.Genre;

public class AuthorDto {
    private Long id;
    private String name;
    private String[] genres;
    private Boolean isApproved;

    public AuthorDto() {
    }

    public AuthorDto(String name, String[] genres) {
        this.name = name;
        this.genres = genres;
    }

    public AuthorDto(Long id, String name, String[] genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
    }

    public AuthorDto(Long id, String name, String[] genres, Boolean isApproved) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.isApproved = isApproved;
    }

    //Для легкого и быстрого создания объекта AuthorDto из Author
    public AuthorDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        if (!author.getAuthorGenres().isEmpty()) {
            this.genres = author.getAuthorGenres().stream().map(Genre::getName).toArray(String[]::new);
        } else { // если у автора нет жанра (жанр был удален, например), то возвращаем массив с пустой строкой,
            // иначе ошибка на фронте
            this.genres = new String[]{""};
        }
        this.isApproved = author.getApproved();
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

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
