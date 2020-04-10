package spring.app.util;

import org.springframework.stereotype.Component;

/**
 * Класс служит для получения необходимых данных из переданных
 * ему Generic-объектов.
 */

@Component
public class GenericHelper<E> {

    /**
     * Метод получает из переданной ему сущности имя класса этой сущности в
     * нижнем регистре.
     *
     * @param entity переданная в метод сущность.
     * @return имя класса этой сущности в нижнем регистре.
     */
    public String getEntityClassNameInLowerCase(E entity) {
        Class entityClass = entity.getClass();
        StringBuilder className = new StringBuilder(entityClass.getSimpleName());
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}