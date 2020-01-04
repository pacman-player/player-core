package spring.app.dto;

import spring.app.model.Genre;
import spring.app.model.PlayList;
import spring.app.model.Song;
import spring.app.model.SongCompilation;

import java.util.List;
import java.util.Set;

public class SongCompilationDto {
    private Long id;
    private String name;
    private Genre genre;
    private Set<Song> setSong;
    private List<PlayList> listPlayList;

    public SongCompilationDto() {
    }

    public SongCompilationDto(Long id, String name, Genre genre, Set<Song> setSong, List<PlayList> listPlayList) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.setSong = setSong;
        this.listPlayList = listPlayList;
    }

    public SongCompilationDto(String name, Genre genre, Set<Song> setSong, List<PlayList> listPlayList) {
        this.name = name;
        this.genre = genre;
        this.setSong = setSong;
        this.listPlayList = listPlayList;
    }
}
