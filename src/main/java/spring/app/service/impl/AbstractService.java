package spring.app.service.impl;

import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenericDao;
import spring.app.service.abstraction.GenericService;

import java.util.List;


@Transactional
public abstract class AbstractService<T, R extends GenericDao<Long, T>> implements GenericService<T> {

    protected final R dao;


    protected AbstractService(R dao) {
        this.dao = dao;
    }

    public void save(T entity) {
        dao.save(entity);
    }

    public T getById(Long id) {
        return dao.getById(id);
    }

    public void update(T entity) {
        dao.update(entity);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public boolean isExistById(Long id) {
        return dao.isExistById(id);
    }

    public List<T> findByNameContaining(String param) {
        return dao.findByNameContaining(param);
    }

    public List<T> getAll() {
        return dao.getAll();
    }

}

