package spring.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Blob;
import java.util.*;

@Entity
@Table(name = "users")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    //@Column(name = "login",  nullable = false, unique = true)
    private String login;

    //@Column(name = "email",  nullable = false, unique = true)
    private String email;

    //@Column(name = "password", length = 30, nullable = false)
    private String password;

    private int vkId;

    private String firstName;

    private String lastName;

    private String googleId;

    @Lob
    @Column(name = "profile_pic")
    private Blob profilePic;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "permissions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    private Company company;

    //@Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "user_on_registrationstep",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "registration_step_id")})
    private List<RegistrationStep> registrationSteps = new ArrayList<>();

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String email, String login, String password, boolean enabled) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
    }

    public User(Long id, String email, String login, String password, boolean enabled) {
        this(email, login, password, enabled);
        this.id = id;
    }

    public User(String googleId, String email, Set<Role> roleSet, boolean enabled) {
        this.email = email;
        this.googleId = googleId;
        this.login = email;
        this.roles = roleSet;
        this.enabled = enabled;
    }

    public User(int vkId, String firstName, String lastName, String email, Set<Role> roles, Company company, Boolean enabled) {
        this.vkId = vkId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.company = company;
        this.enabled = enabled;
    }

    public Blob getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Blob profilePic) {
        this.profilePic = profilePic;
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

    public List<RegistrationStep> getRegistrationSteps() {
        return registrationSteps;
    }

    public void setRegistrationSteps(List<RegistrationStep> registrationSteps) {
        this.registrationSteps = registrationSteps;
    }

    public void addRegStep(RegistrationStep registrationStep) {
        registrationSteps.add(registrationStep);
        registrationStep.getUsersOnStep().add(this);
    }

    public void removeRegStep(RegistrationStep registrationStep) {
        registrationSteps.remove(registrationStep);
        registrationStep.getUsersOnStep().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String toString() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getVkId() {
        return vkId;
    }

    public void setVkId(int vkId) {
        this.vkId = vkId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return vkId == user.vkId &&
                id.equals(user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(googleId, user.googleId) &&
                Objects.equals(company, user.company) &&
                enabled.equals(user.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, vkId, firstName, lastName, googleId, company, enabled);
    }
}