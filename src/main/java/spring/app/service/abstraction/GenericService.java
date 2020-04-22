package spring.app.service.abstraction;

import spring.app.dao.abstraction.GenericDao;

import java.io.Serializable;
import java.util.List;

public interface GenericService<PK extends Serializable, T> {

    void save(T entity);

    T getById(PK id);

    void update(T entity);

    void deleteById(PK id);

    boolean isExistById(PK id);

    List<T> findByNameContaining(String param);

    List<T> getAll();

}


