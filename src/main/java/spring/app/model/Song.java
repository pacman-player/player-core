package spring.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "song")
public class Song implements Bannable{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /*
     @JsonIgnore нужна для того, что бы запрос на получение списка песен работал корректно
     без этой аннотации запрос на выбор сущностей данных падает с ошибкой, скорее всего это связанно с неверным
     мапингом на другие таблицы. С этой аннотацией в получаемой сущности не будет этих полей.
     Предполагаемое решение :)
     https://stackoverflow.com/questions/36983215/failed-to-write-http-message-org-springframework-http-converter-httpmessagenotw/45822490
    */

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    private Genre genre;

    @OneToMany(mappedBy = "song")
    @JsonIgnore
    private Set<SongQueue> song;

    @Transient
    private Boolean banned;

    public Song() {
    }

    public Song(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        id = id;
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

    public Set<SongQueue> getSong() {
        return song;
    }

    public void setSong(Set<SongQueue> song) {
        this.song = song;
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
                ", song=" + song +
                '}';
    }
}
