package spring.app.dto;

import java.util.Set;

/**
 * Created by User on 13.03.2018.
 */
public class UserDto {
    private Long id;
    private String login;
    private String email;
    private String password;
    private Boolean enabled = true;
    private Set<String> roles;
    private Long companyId;

    public UserDto(Long id, String login, String email, String password, Set<String> roles) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDto(String login, String email, String password, Set<String> roles) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDto() {

    }

    public UserDto(long id, String login, String email, String password, Boolean enabled) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }
    //конструктор для выбора email пользователей без компании getUsersEmailWithoutCompany()
    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != null ? !id.equals(userDto.id) : userDto.id != null) return false;
        if (login != null ? !login.equals(userDto.login) : userDto.login != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        if (enabled != null ? !enabled.equals(userDto.enabled) : userDto.enabled != null) return false;
        if (roles != null ? !roles.equals(userDto.roles) : userDto.roles != null) return false;
        return companyId != null ? companyId.equals(userDto.companyId) : userDto.companyId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }
}
