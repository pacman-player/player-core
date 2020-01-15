package spring.app.util;
/**
 * This class provides a simplified query control option
 * How to use:
 * QueryCounter.startCounting(); //start counting
 * //make your database operations
 * int executedQueries = QueryCounter.getCountOfQueries();; //get count of queries
 * QueryCounter..assertQueryCount(expectedQueryQuantity); //or you can check that it was made expected quantity of queries
 */
public class QueryCounter {
    public static void startCounting() {
        DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.resetQueryCounter();
    }

    public static int getCountOfQueries() {
        return DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.queryCounter;
    }

    public static void assertQueryCount(int expectedQueryQuantity) {
        DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.assertQueryCount(expectedQueryQuantity);
    }
}
