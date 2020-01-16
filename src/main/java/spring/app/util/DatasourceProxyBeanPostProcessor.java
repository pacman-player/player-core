package spring.app.util;

import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * Класс для подсчёта выполненных запросов в БД с использованием прокси
 * Как использовать:
 * DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.resetQueryCounter(); // обнулить счётчик
 * // операции с БД
 * int executedQueries = DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.queryCounter; // получить текущее количество выполненных запросов
 * DatasourceProxyBeanPostProcessor.ProxyDataSourceInterceptor.assertQueryCount(expectedQueryQuantity); // проверить на количество выполненных запросов
 */
@Component
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource && !(bean instanceof ProxyDataSource)) {
            final ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
            return factory.getProxy();
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public static class ProxyDataSourceInterceptor implements MethodInterceptor {
        private final DataSource dataSource;
        private static boolean count = false;
        public static int queryCounter = 0;

        public static void startCounting() {
            resetQueryCounter();
            count = true;
        }

        public static void stopCounting() {
            count = false;
        }

        public static void resetQueryCounter() {
            queryCounter = 0;
        }

        public ProxyDataSourceInterceptor(final DataSource dataSource) {
            this.dataSource = ProxyDataSourceBuilder.create(dataSource)
                    .afterQuery((execInfo, queryInfoList) -> {
                        if (count) {
                            queryCounter++;
                        }
                    })
                    .build();
        }

        public static boolean assertQueryCount(int expectedQueryQuantity) {
            if (expectedQueryQuantity != queryCounter) {
                throw new RuntimeException("hibernate generated " + queryCounter + " queries, but you expected " + expectedQueryQuantity + " queries");
            }
            return true;
        }

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            final Method proxyMethod = ReflectionUtils.findMethod(this.dataSource.getClass(),
                    invocation.getMethod().getName());
            if (proxyMethod != null) {
                return proxyMethod.invoke(this.dataSource, invocation.getArguments());
            }
            return invocation.proceed();
        }
    }
}