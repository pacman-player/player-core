package spring.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

import java.util.Map;

@Entity
@Table(name = "registration_step")
public class RegistrationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "users_id")
    @JoinTable(name = "steps_user",
            joinColumns = @JoinColumn(name = "steps_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Map<User, Company> userCompanies;

    public RegistrationStep() {
    }

    public RegistrationStep(Long id, String name, Long position, Map<User, Company> userCompanies) {
        this.id = id;
        this.name = name;
        this.userCompanies = userCompanies;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<User, Company> getUserCompanies() {
        return userCompanies;
    }

    public void setUserCompanies(Map<User, Company> userCompanies) {
        this.userCompanies = userCompanies;
    }

}
