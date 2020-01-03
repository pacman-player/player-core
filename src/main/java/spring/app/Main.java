package spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.app.configuration.initializer.TestDataInit;
import spring.app.util.EmailSender;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Main extends WebMvcConfigurerAdapter {

    public Main() throws MessagingException {
        emailSender.send("Заголовок", "Это текст", "pacmancore26@gmail.com","evgenykalashnikov26@gmail.com" );

    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private static EmailSender emailSender;

    static {
        try {
            emailSender = new EmailSender("pacmancore26@gmail.com", "G7682121g");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    @Bean(initMethod = "init")
    @PostConstruct
    public TestDataInit initTestData() {
        return new TestDataInit();
    }
}