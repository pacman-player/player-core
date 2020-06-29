package spring.app.dto;

import spring.app.model.Company;
import spring.app.model.Song;

public class SongQueueDto {

    private Long id;
    private Long position;
    private Song song;
    private Company company;


    public SongQueueDto(Long id, Long position, Song song, Company company) {
        this.id = id;
        this.position = position;
        this.song = song;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
