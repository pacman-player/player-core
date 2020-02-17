package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.catalina.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "registration_step")
public class RegistrationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = UserCompany.class)
    @JoinTable(name = "UserCompanyKey_RegistrationStep",
            joinColumns = {@JoinColumn(name = "registrationStep_id")},
            inverseJoinColumns = {@JoinColumn(name = "userCompanyKey_id")})
    private List<UserCompany> userCompanies;

    public RegistrationStep() {
    }

    public RegistrationStep(Long id, String name, Long position, List<UserCompany> userCompanies) {
        this.id = id;
        this.name = name;
        this.userCompanies = userCompanies;
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

    public List<UserCompany> getUserCompanies() {
        return userCompanies;
    }

    public void setUserCompanies(List<UserCompany> userCompanies) {
        this.userCompanies = userCompanies;
    }

}
