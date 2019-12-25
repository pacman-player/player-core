package spring.app.dto;

public class AuthorDto {

    private Long id;

    private String name;

    private String authorGenres;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAuthorGenres(){
        return this.authorGenres;
    }

    public void setAuthorGenres(String authorGenres){
        this.authorGenres = authorGenres;
    }

}
