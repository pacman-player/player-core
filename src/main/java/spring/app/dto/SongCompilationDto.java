package spring.app.dto;

import java.util.List;
import java.util.Set;

public class SongCompilationDto {
    private Long id;
    private String name;
    private String genre;
    private Set<SongDto> setSongDto;
    private List<PlayListDto> listPlayListDto;

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
}
