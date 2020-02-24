package spring.app.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class SongCompilationDto {
    private Long id;
    private String name;
    private String genre;
    private Set<SongDto> setSongDto;
    private List<PlayListDto> listPlayListDto;
    private MultipartFile cover;

    public SongCompilationDto() {
    }

    public SongCompilationDto(Long id, String name, String genre, Set<SongDto> setSongDto, List<PlayListDto> listPlayListDto) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.setSongDto = setSongDto;
        this.listPlayListDto = listPlayListDto;
    }

    public SongCompilationDto(String name, String genre, Set<SongDto> setSongDto, List<PlayListDto> listPlayListDto) {
        this.name = name;
        this.genre = genre;
        this.setSongDto = setSongDto;
        this.listPlayListDto = listPlayListDto;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<SongDto> getSetSongDto() {
        return setSongDto;
    }

    public void setSetSongDto(Set<SongDto> setSongDto) {
        this.setSongDto = setSongDto;
    }

    public List<PlayListDto> getListPlayListDto() {
        return listPlayListDto;
    }

    public void setListPlayListDto(List<PlayListDto> listPlayListDto) {
        this.listPlayListDto = listPlayListDto;
    }

    public MultipartFile getCover() {
        return cover;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }
}
