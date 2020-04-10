package core.app.service.abstraction;

public interface GenericService<E> {

    void deleteEntity(E entity);

    void save(E entity);
}
