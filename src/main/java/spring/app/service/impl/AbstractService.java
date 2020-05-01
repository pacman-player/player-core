package spring.app.service.impl;

import spring.app.dao.abstraction.GenericDao;
import spring.app.service.abstraction.GenericService;

import java.io.Serializable;
import java.util.List;


public abstract class AbstractService<PK extends Serializable, T, R extends GenericDao<PK, T>> implements GenericService<PK, T> {

    final protected R dao;

    protected AbstractService(R dao) {
        this.dao = dao;
    }

}

