package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinTable(name = "permissions",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<User> users;

    public Role() {
    }

    public Role(String name) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        if (name.contains("ADMIN") && name.contains("USER")) {
            return "admin, user";
        } else if (name.contains("ADMIN")) {
            return "admin";
        }
        return "user";
    }

    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) &&
                name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}