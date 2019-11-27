package spring.app.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime closeTime;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Company.class)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Company.class)
    private OrgType orgType;
}