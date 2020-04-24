package spring.app.service.impl;

import spring.app.dao.abstraction.GenericDao;

import java.io.Serializable;
import java.util.List;


public abstract class AbstractService<PK extends Serializable, T, R extends GenericDao<PK, T>>  {

    final protected R dao;

    protected AbstractService(R dao) {
        this.dao = dao;
    }

    public abstract void save(T entity);

    public abstract T getById(PK id);

    public abstract void update(T entity);

    public abstract void deleteById(PK id);

    public abstract boolean isExistById(PK id);

    public abstract List<T> findByNameContaining(String param);

    public abstract List<T> getAll();

}

