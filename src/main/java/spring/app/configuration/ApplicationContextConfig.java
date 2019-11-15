package spring.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;


@Configuration
//@EnableWebMvc
@ComponentScan("spring.app")
@EnableTransactionManagement
public class ApplicationContextConfig {

	@Bean
	public TemplateResolver springThymeleafTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setOrder(1);
		resolver.setCacheable(false);
		return resolver;
	}

	/*@Bean(initMethod = "init")
	@Autowired
	public TestDataInit initTestData(UserService userService, RoleService roleService) {
		return new TestDataInit(userService, roleService);
	}*/
}
