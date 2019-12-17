package spring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.app.configuration.initializer.TestDataInit;
import spring.app.service.abstraction.ZaycevSaitServise;
import spring.app.service.impl.KrolikSaitImpl;
import spring.app.service.impl.ZaycevSaitImpl;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Main extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        //для тестирования Zaycev
        /*ZaycevSaitImpl zaycevSaitServise = new ZaycevSaitImpl();
        zaycevSaitServise.getSong("ария", "штиль");*/

        //для тестирования Krolik
        KrolikSaitImpl krolikSait = new KrolikSaitImpl();
        krolikSait.getSong("король и шут", "сосиска");

    }


    @Bean(initMethod = "init")
    @PostConstruct
    public TestDataInit initTestData() {
        return new TestDataInit();
    }
}