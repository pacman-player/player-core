package spring.app.dto;

import org.springframework.web.multipart.MultipartFile;


public class TestDto {
    private Long id;
    private String name;
    private MultipartFile cover;

    public TestDto() {
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

    public MultipartFile getCover() {
        return cover;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }
}
