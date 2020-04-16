package spring.app.dao.impl;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public abstract class AbstractDao<PK extends Serializable, T> {

    @PersistenceContext
    EntityManager entityManager;

    private final Class<T> persistentClass;

    AbstractDao(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public T getById(PK id) {
        return entityManager.find(persistentClass, id);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void deleteById(PK id) {
        T entity = entityManager.find(persistentClass, id);
        entityManager.remove(entity);
    }

    /**
     * возвращает List объектов, в имени которых содержиться передаваемый парраметр
     * Если у обекта нет поля 'name', возвращает null
     *
     * @return List
     */
    public List<T> findByNameContaining(String param) {
        try {
            String className = persistentClass.getName();

            String hql = "FROM " + className + " o WHERE o.name LIKE :param";

            TypedQuery<T> query = entityManager.createQuery(hql, persistentClass);
            // знак % обозначает, что перед передаваемым значение может быть, или колько угодно символов, или ноль.
            query.setParameter("param", "%" + param + "%");

            return query.getResultList();

        } catch (Exception e) {
            System.out.println("сообщение об ошибке: " + e.getMessage());
            return null;
        }
    }

    public List<T> getAll() {
        String genericClassName = persistentClass.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String hql = "FROM " + genericClassName;
        TypedQuery<T> query = entityManager.createQuery(hql, persistentClass);
        return query.getResultList();
    }

}