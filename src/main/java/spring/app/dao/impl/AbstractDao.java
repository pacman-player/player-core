package spring.app.dao.impl;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractDao<PK extends Serializable, T> {
	@PersistenceContext
	EntityManager entityManager;

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	AbstractDao(Class<T> persistentClass){
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
		T entity =  entityManager.find(persistentClass, id);
		entityManager.remove(entity);
	}

	public List<T> getAll() {
		String genericClassName = persistentClass.toGenericString();
		genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
		String hql = "FROM " + genericClassName;
		TypedQuery<T> query = entityManager.createQuery(hql, persistentClass);
		return query.getResultList();
	}
}
