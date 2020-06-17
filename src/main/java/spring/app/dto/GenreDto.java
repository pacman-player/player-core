package spring.app.dto;

import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.model.Genre;

import java.sql.Timestamp;

public class GenreDto extends Bannable {

    private Long id;
    private String name;
    private Timestamp createdAt;
    private Boolean isApproved;
    private String keywords;

    public GenreDto() {
    }

public GenreDto(Long id, String name, Boolean isApproved) {
        this.id = id;
        this.name = name;
        this.isApproved = isApproved;
    }
    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDto(String name) {
        this.name = name;
    }

    public GenreDto(String name, Boolean isApproved) {
        this.name = name;
        this.isApproved = isApproved;
    }

    public GenreDto(Long id, String name, Boolean isApproved, String keywords) {
        this.id = id;
        this.name = name;
        this.isApproved = isApproved;
        this.keywords = keywords;
    }

    //Для легкого и быстрого создания объекта GenreDto из Genre
    public GenreDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.createdAt = genre.getCreatedAt();
        this.isApproved = genre.getApproved();
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public void setBanned(boolean banned) {

    }

    @Override
    public boolean isBannedBy(Company company) {
        return false;
    }
}
