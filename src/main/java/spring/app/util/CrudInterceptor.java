package spring.app.util;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class CrudInterceptor extends EmptyInterceptor {
    private static int count;

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        CrudInterceptor.count = count;
    }

    public static void reset() {
        count = 0;
    }

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