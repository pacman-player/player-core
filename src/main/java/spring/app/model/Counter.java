package spring.app.model;

import javax.persistence.*;

@Entity
@Table(name = "t_counter")
public class Counter {
    @Id
    private String counterType;

    @Column(name = "value")
    private Integer value;

    public Counter() {

    }

    public Counter(String counterType) {
        this.counterType = counterType;
    }

    public Counter(String counterType, Integer value) {
        this.counterType = counterType;
        this.value = value;
    }


    public String getCounterType() {
        return counterType;
    }

    public void setCounterType(String counterType) {
        this.counterType = counterType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
