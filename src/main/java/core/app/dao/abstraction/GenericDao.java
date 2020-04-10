package core.app.dao.abstraction;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<PK extends Serializable, T> {

    void save(T entity);

    T getById(PK id);

    List<T> getAll();

    List<T> getAllApproved();

    void update(T group);

    void deleteById(PK id);

    void deleteEntity(T entity);

    /**
     * Возвращает список сущностей, в имени которых содержиться переданный параметр
     *
     * @return List<Song>
     */
    List<T> findByNameContaining(String param);

}
