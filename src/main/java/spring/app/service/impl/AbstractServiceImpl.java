package spring.app.service.impl;

import spring.app.dao.abstraction.GenericDao;
import java.io.Serializable;
import java.util.List;


public abstract class AbstractServiceImpl<T, R extends GenericDao<PK , T>, PK extends Serializable> extends AbstractService<PK, T> {

    protected final R dao;

    protected AbstractServiceImpl(R dao) {
        this.dao = dao;
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

