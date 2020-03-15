package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "registration_steps")
public class RegistrationStep {

    @Id
    @Column(name = "registration_step_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "registrationSteps",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<User> usersOnStep = new ArrayList<>();

    public RegistrationStep() {
    }

    public RegistrationStep(Long id, String name, Long position) {
        this.id = id;
        this.name = name;
    }

    public RegistrationStep(String name) {
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

    public List<User> getUsersOnStep() {
        return usersOnStep;
    }

    public void setUsersOnStep(List<User> usersOnStep) {
        this.usersOnStep = usersOnStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationStep that = (RegistrationStep) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
