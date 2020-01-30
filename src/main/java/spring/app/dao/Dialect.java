package spring.app.dao;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.stereotype.Component;

@Component
public class Dialect extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
