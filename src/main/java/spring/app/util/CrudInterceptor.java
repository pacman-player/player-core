package spring.app.util;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class CrudInterceptor extends EmptyInterceptor {
    private static int count;

    @Override
    public boolean onLoad(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        count++;
        System.out.println("Load method called " + count + " times");
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        count++;
        System.out.println("Save method called " + count + " times");
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        System.out.println("Ondelete Method Called: " + entity + " SerializableId: " + id + " state: " + state);
    }
}