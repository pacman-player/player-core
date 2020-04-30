package spring.app.service.impl;

import spring.app.dao.abstraction.GenericDao;
import java.io.Serializable;
import java.util.List;


public abstract class AbstractServiceImpl<PK extends Serializable, T, R extends GenericDao<PK, T>> extends AbstractService<PK, T, R> {

    protected AbstractServiceImpl(R dao) {
        super(dao);
    }

    public void save(T entity) {
        dao.save(entity);
    }

    public T getById(PK id) {
        return dao.getById(id);
    }

    public void update(T entity) {
        dao.update(entity);
    }

    public void deleteById(PK id) {
        dao.deleteById(id);
    }

    public boolean isExistById(PK id) {
        return dao.isExistById(id);
    }

    public List<T> findByNameContaining(String param) {
        return dao.findByNameContaining(param);
    }

    public List<T> getAll() {
        return dao.getAll();
    }

}

