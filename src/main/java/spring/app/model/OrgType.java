package spring.app.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class OrgType {
    @Id
    private Long id;
    private String name;
}
