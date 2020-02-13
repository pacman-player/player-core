package spring.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import spring.app.model.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

public class CompanyUCDto {
    private Long id;
    private String name;

    public CompanyUCDto() {
    }

    public CompanyUCDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

}
