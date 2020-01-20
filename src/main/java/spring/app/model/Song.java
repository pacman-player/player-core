package spring.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import spring.app.dto.SongDto;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "song")
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
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL) //здесь убрал orphanRemoval = true тк не удалялась последняя песня
    private Set<SongQueue> songQueues;

    //    @Fetch(FetchMode.SUBSELECT) //для решения возможной проблемы N+1 только у ManyToMany, закомментил тк все норм работает
    @ManyToMany(fetch = FetchType.LAZY) //нельзя ставить у ManyToMany cascade = CascadeType.ALL - сущности все удаляться по цепочке
    @JoinTable(name = "song_compilation_on_song",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_compilation_id")})
    private Set<SongCompilation> songCompilations;

    /**
     * Вспомогательное поле, кокоторое используеться фронтом для корректного отображения данных.
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

    public Set<SongCompilation> getSongCompilations() {
        return songCompilations;
    }

    public void setSongCompilations(Set<SongCompilation> songCompilations) {
        this.songCompilations = songCompilations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Song(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
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

    @Override
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean isBannedBy(Company company) {
        return company.getBannedSong().contains(this);
    }

    public Boolean getBanned() {
        return banned;
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

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", songQueue=" + songQueues +
                '}';
    }
}
