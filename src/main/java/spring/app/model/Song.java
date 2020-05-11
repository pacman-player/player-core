package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import spring.app.dto.SongDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "songs")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "SongDtoMapping",
                classes = @ConstructorResult(
                        targetClass = SongDto.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name")
                        }
                )
        )
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Song extends Bannable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "search_tags")
    private String searchTags;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    //здесь убрал orphanRemoval = true тк не удалялась последняя песня
    private Set<SongQueue> songQueues;

    //    @Fetch(FetchMode.SUBSELECT) //для решения возможной проблемы N+1 только у ManyToMany, закомментил тк все норм работает
    @ManyToMany(fetch = FetchType.LAZY)
    //нельзя ставить у ManyToMany cascade = CascadeType.ALL - сущности все удаляться по цепочке
    @JoinTable(name = "song_compilation_on_song",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_compilation_id")})
    private Set<SongCompilation> songCompilations;

    private String albumsCover;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "approved")
    private Boolean isApproved = false;


    /**
     * Вспомогательное поле, которое используеться фронтом для корректного отображения данных.
     */
    @Transient
    private Boolean banned;

    public Song() {
    }

    public Song(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Song(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Song(Long id, String name, Author author, Genre genre, Set<SongQueue> songQueues) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.songQueues = songQueues;
    }

    public Song(Long id, String name, Genre genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    public Song(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Song(String name, Author author, Genre genre, String searchTags) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.searchTags = searchTags;
    }

    public Song(String name) {
        this.name = name;
    }


    public Set<SongCompilation> getSongCompilations() {
        return songCompilations;
    }

    public void setSongCompilations(Set<SongCompilation> songCompilations) {
        this.songCompilations = songCompilations;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<SongQueue> getSongQueues() {
        return songQueues;
    }

    public void setSongQueues(Set<SongQueue> songQueues) {
        this.songQueues = songQueues;
    }

    public String getAlbumsCover() {
        return albumsCover;
    }

    public void setAlbumsCover(String albumsCover) {
        this.albumsCover = albumsCover;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @Override
    public boolean isBannedBy(Company company) {
        return company.getBannedSong().contains(this);
    }

    public Boolean getBanned() {
        return banned;
    }

    @Override
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(String searchTags) {
        this.searchTags = searchTags;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song1 = (Song) o;
        return id.equals(song1.id) &&
                name.equals(song1.name);
    }
}
