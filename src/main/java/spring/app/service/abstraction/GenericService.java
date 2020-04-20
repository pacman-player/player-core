package spring.app.service.abstraction;

import spring.app.dao.abstraction.GenericDao;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T> {

    void save(T entity);

    T getById(Long id);

    void update(T entity);

    void deleteById(Long id);

    boolean isExistById(Long id);

    List<T> findByNameContaining(String param);

    List<T> getAll();

}


