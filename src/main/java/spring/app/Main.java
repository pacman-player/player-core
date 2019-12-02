package spring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.app.configuration.initializator.TestDataInit;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Main extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean(initMethod = "init")
	@PostConstruct
	public TestDataInit initTestData() {
		return new TestDataInit();
	}

}
