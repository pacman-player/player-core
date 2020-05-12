package spring.app.model;

import javax.persistence.*;

@Entity
@Table(name = "t_counter")
public class Counter {
    @Id
    @Enumerated(EnumType.STRING)
    private CounterType counterType;

    @Column(name = "value")
    private Integer value;

    public Counter() {

    }

    public Counter(CounterType counterType) {
        this.counterType = counterType;
    }

    public Counter(CounterType counterType, Integer value) {
        this.counterType = counterType;
        this.value = value;
    }


    public CounterType getCounterType() {
        return counterType;
    }

    public void setCounterType(CounterType counterType) {
        this.counterType = counterType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
