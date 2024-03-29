package spring.app.dto;

import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.model.Song;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

public class SongDto extends Bannable {

    private Long id;
    private String name;
    private String authorName;
    private Long authorId;
    private String genreName;
    private Set<String> searchTags;
    private Timestamp createdAt;
    private Boolean isApproved;
    private MultipartFile file;
    private boolean banned;

    private AuthorDto authorDto;

    public SongDto(Long id, String name, String authorName, String genreName, Set<String> searchTags, Timestamp createdAt, Boolean isApproved) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
        this.searchTags = searchTags;
        this.createdAt = createdAt;
        this.isApproved = isApproved;
    }

    public SongDto(Long id, String name, Boolean isApproved, String authorName, Long authorId, String genreName) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.authorId = authorId;
        this.genreName = genreName;
        this.isApproved = isApproved;
    }

    public SongDto(Long id, String name, Boolean isApproved, String authorName, String genreName) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
        this.isApproved = isApproved;
    }

    public SongDto(Long id, String name, Boolean isApproved, AuthorDto authorDto) {
        this.id = id;
        this.name = name;
        this.isApproved = isApproved;
        this.authorDto = authorDto;
    }

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

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

    public SongDto(Long id, String name, Boolean isApproved, String authorName) {
        this.id = id;
        this.name = name;
        this.isApproved = isApproved;
        this.authorName = authorName;
    }

    public SongDto() {
    }

    //Для легкого и быстрого создания объекта Dto
    public SongDto(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.authorName = song.getAuthor().getName();
        if (song.getAuthor().getAuthorGenres() == null) {// если у автора нет жанра (жанр был удален, например),
            this.genreName = "";      // то возвращаем пустую строк иначе ошибка на фронте
        } else {
            this.genreName = song.getAuthor().getAuthorGenres().toString();
        }
        this.searchTags = song.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toSet());
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Set<String> getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(Set<String> searchTags) {
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public boolean isBanned() {
        return banned;
    }

    @Override
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean isBannedBy(Company company) {
        return company.getBannedSong().contains(new Song(id, name));
    }

    @Override
    public String toString() {
        return "SongDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorId='" + authorId + '\'' +
                ", searchTags='" + searchTags + '\'' +
                ", genreName='" + genreName + '\'' +
                ", createdAt=" + createdAt +
                ", isApproved=" + isApproved +
                '}';
    }
}
