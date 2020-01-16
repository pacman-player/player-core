package spring.app.util;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для подсчёта выполненных запросов в БД с использованием hibernate statistics
 * Как использовать:
 * QueryCounter.startCounting(); // начать отсчёт
 * // операции с БД
 * int executedQueries = QueryCounter.getCountOfQueries(); // получить текущее количество выполненных запросов
 * QueryCounter..assertQueryCount(expectedQueryQuantity); // проверить на количество выполненных запросов
 */
@Component
public class QueryCounter {

    @PersistenceContext
    private EntityManager entityManager;

    private static long startQuantity = 0;
    private static long endQuantity = 0;

    public void startCounting() {
        Session session = (Session) entityManager.getDelegate();
        startQuantity = session.getSessionFactory().getStatistics().getPrepareStatementCount();
    }

    public void startProxyCounting() {
        DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.startCounting();
    }

    public void testObject(Object object) {
        Session session = (Session) entityManager.getDelegate();
        List<String> entityNames = Arrays.asList(session.getSessionFactory().getStatistics().getEntityNames());
        if (object instanceof List) {
            for (Object listItem : (List) object) {
                testObject(listItem);
            }
        }
        System.out.println(object.getClass().getName());
        for (Field objectField : object.getClass().getFields()) {
            System.out.println(objectField);
        }
    }

    public void stopCounting() {
        Session session = (Session) entityManager.getDelegate();
        endQuantity = session.getSessionFactory().getStatistics().getPrepareStatementCount();
    }

    public void stopProxyCounting() {
        DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.stopCounting();
    }

    public long getCountOfQueries() {
        return endQuantity - startQuantity;
    }

    public long getProxyCountOfQueries() {
        return DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.queryCounter;
    }

    public boolean assertQueryCount(long expectedQueryQuantity) {
        if (expectedQueryQuantity != endQuantity - startQuantity) {
            throw new RuntimeException("hibernate generated " + (endQuantity - startQuantity) + " queries, but you expected " + expectedQueryQuantity + " queries");
        }
        return true;
    }
}
