package spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.app.configuration.initializer.TestDataInit;
import spring.app.service.abstraction.ZaycevSaitServise;
import spring.app.service.impl.ZaycevSaitImpl;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Main extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		//для стирования
		ZaycevSaitImpl zaycevSaitServise = new ZaycevSaitImpl();
		zaycevSaitServise.searchSongByAuthorOrSongs("", "улица роз");

	}

	@Bean(initMethod = "init")
	@PostConstruct
	public TestDataInit initTestData() {
		return new TestDataInit();
	}
}