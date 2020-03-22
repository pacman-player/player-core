package spring.app.dto;

import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;

import java.util.List;

public class AuthorSongGenreListDto {
    private List<Author> authors;
    private List<Song> songs;
    private List<Genre> genres;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
