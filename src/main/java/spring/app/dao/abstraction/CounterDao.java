package spring.app.dao.abstraction;

public interface CounterDao {

    void start(String counterType);

    int restart(String counterType);

    void setTo(String counterType, int value);

    int getValue(String counterType);
}
