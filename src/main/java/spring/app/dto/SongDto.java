package spring.app.dto;

import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.SongQueue;

import java.util.Set;

public class SongDto {

    private Long id;
    private String name;
    private Author author;
    private Genre genre;
    private Set<SongQueue> songQueue;

    public SongDto(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public SongDto(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public SongDto(Long id, String name, Author author, Genre genre, Set<SongQueue> songQueue) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.songQueue = songQueue;
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

    public Set<SongQueue> getSongQueue() {
        return songQueue;
    }

    public void setSongQueue(Set<SongQueue> songQueue) {
        this.songQueue = songQueue;
    }

}
