package spring.app.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.userdetails.UserDetails;
import spring.app.model.Company;
import spring.app.model.Role;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

public class UserUCDto {
    private Long id;

    private String login;

    private String email;

    private CompanyUCDto companyUCDto;


    public UserUCDto() {
    }

    public UserUCDto(Long id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public UserUCDto(Long id, String login, String email, CompanyUCDto companyUCDto) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.companyUCDto = companyUCDto;
    }

    public UserUCDto(Long id, String login, String email, Long companyId, String companyName) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.companyUCDto = new CompanyUCDto(companyId, companyName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CompanyUCDto getCompanyUCDto() {
        return companyUCDto;
    }

    public void setCompanyUCDto(CompanyUCDto companyUCDto) {
        this.companyUCDto = companyUCDto;
    }
}
