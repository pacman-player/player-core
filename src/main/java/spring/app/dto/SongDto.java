package spring.app.dto;

public class SongDto {

    private Long id;
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

    private String name;
    private String authorName;
    private String genreName;

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

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
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
                ", isApproved=" + isApproved +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
