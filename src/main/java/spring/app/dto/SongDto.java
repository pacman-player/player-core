package spring.app.dto;

import spring.app.model.Song;

import java.sql.Timestamp;

public class SongDto {

    private Long id;
    private String name;
    private String authorName;
    private String searchTags;
    private String genreName;
    private Timestamp createdAt;
    private Boolean isApproved;

    public SongDto(Boolean isApproved, String name, String authorName, String genreName) {
        this.isApproved = isApproved;
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public SongDto(Long id, Boolean isApproved, String name, String authorName, String genreName) {
        this.id = id;
        this.isApproved = isApproved;
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public SongDto(Long id, String name, String authorName, String genreName) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public SongDto(String name, String authorName, String genreName) {
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public SongDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SongDto(Long id, String name, String genreName) {
        this.id = id;
        this.name = name;
        this.genreName = genreName;
    }

    public SongDto() {
    }

    //Для легкого и быстрого создания объекта AuthorDto из Author
    public SongDto(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.authorName = song.getAuthor().getName();
        if (song.getGenre() == null) {// если у автора нет жанра (жанр был удален, например),
            this.genreName = "";      // то возвращаем пустую строк иначе ошибка на фронте
        } else {
            this.genreName = song.getGenre().getName();
        }
        if (song.getSearchTags() == null) {
            this.searchTags = "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            song.getSearchTags().forEach(s -> stringBuilder.append(s).append(" "));
            this.searchTags = stringBuilder.toString();
        }
        this.createdAt = song.getCreatedAt();
        this.isApproved = song.getApproved();
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(String searchTags) {
        this.searchTags = searchTags;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "SongDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", searchTags='" + searchTags + '\'' +
                ", genreName='" + genreName + '\'' +
                ", createdAt=" + createdAt +
                ", isApproved=" + isApproved +
                '}';
    }
}
