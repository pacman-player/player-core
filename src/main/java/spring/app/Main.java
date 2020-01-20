package spring.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.app.configuration.initializer.TestDataInit;
import spring.app.service.EmailSender;
import spring.app.service.impl.KrolikSaitServiceImpl;
import spring.app.service.impl.ZaycevSaitServiceImpl;
import spring.app.testPlayer.config.TestMusicDataInit;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.ServerSocket;

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

    @Bean(initMethod = "init")
    @PostConstruct
    public TestMusicDataInit testMusicDataInit() {
        return new TestMusicDataInit();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

   /* @Bean
    public ServerSocket getServer() throws IOException {
        return new ServerSocket(portNotification);
    }*/
}