package spring.app.dao.abstraction;

import spring.app.model.Counter;
import spring.app.model.CounterType;

public interface CounterDao {

    void start(CounterType counterType);

    Counter restart(CounterType counterType);

    void setTo(CounterType counterType, int value);

    int getValue(CounterType counterType);
}
