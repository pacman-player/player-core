package spring.app.util;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Класс является надстройкой над hibernate
 * с помощью данного класса-утилиты можно контроллировать каждый запрос, делаемый хибернейтом
 * нужно прсто перепределить необходимые методы EmptyInterceptor
 * На данном этапе он настроен для подсчёта количества выполненных запросов
 */
@Component
public class HibernateInterceptor extends EmptyInterceptor {
    private static int queryCounter = 0;
    private static int lastQueryCount = 0;
    @Autowired
    private EntityManager entityManager;
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession() {
        session = (Session) entityManager.getDelegate();
    }

    public static void startCounting() {
        queryCounter = 0;
    }

    public static void stopCounting() {
        lastQueryCount = queryCounter;
    }

    public static int getQueryCounter() {
        return queryCounter;
    }

    @Override
    public String onPrepareStatement(String sql) {
        queryCounter++;
        System.out.println("GENERATING SQL QUERY");
        return super.onPrepareStatement(sql);
    }
}
