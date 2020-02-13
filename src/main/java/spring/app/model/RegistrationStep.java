package spring.app.model;

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
    private Long position;
    private String scriptName;

    public RegistrationStep() {
    }

    public RegistrationStep(Long id, String name, Long position, String scriptName) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.scriptName = scriptName;
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

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }
}
