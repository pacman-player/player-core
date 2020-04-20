package spring.app.service.impl;

import spring.app.dao.abstraction.GenericDao;
import spring.app.service.abstraction.GenericService;

import java.util.List;


public abstract class AbstractService<T> implements GenericService<T> {

    public abstract void save(T entity);

    public abstract T getById(Long id);

    public abstract void update(T entity);

    public abstract void deleteById(Long id);

    public abstract boolean isExistById(Long id);

    public abstract List<T> findByNameContaining(String param);

    public abstract List<T> getAll();

}

