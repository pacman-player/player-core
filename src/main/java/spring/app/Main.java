package spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.app.configuration.initializer.TestDataInit;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableAsync
@EnableCaching
public class Main extends WebMvcConfigurerAdapter {

    /*@Value("${portNotification}")
    private int portNotification;*/

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

//        //для тестирования Zaycev
//        ZaycevSaitServiceImpl zaycevSaitServise = new ZaycevSaitServiceImpl();
//        zaycevSaitServise.getSong("ария", "штиль");
//
//        //для тестирования Krolik
//        KrolikSaitServiceImpl krolikSait = new KrolikSaitServiceImpl();
//        krolikSait.getSong("король и шут", "сосиска");

    }

    @Bean(initMethod = "init")
    @PostConstruct
    public TestDataInit initTestData() {
        return new TestDataInit();
    }


}