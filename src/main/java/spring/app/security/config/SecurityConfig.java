package spring.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import spring.app.security.handlers.CustomAuthenticationFailureHandler;
import spring.app.security.handlers.CustomAuthenticationSuccessHandler;
import spring.app.security.handlers.CustomLogoutSuccessHandler;
import spring.app.security.service.UserDetailsServiceImpl;

@Configuration
@ComponentScan("spring.app")
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl authenticationService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl authenticationService,
                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                          CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.authenticationService = authenticationService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.csrf().disable().addFilterBefore(filter, CsrfFilter.class);

        http
                .authorizeRequests()
                .antMatchers("/api/**","/registration**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/processing-url")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .usernameParameter("login")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll();

		/*
				/*.and()
				.exceptionHandling()
				.accessDeniedPage("/error");*/
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //The name of the configureGlobal method is not important. However,
        // it is important to only configure AuthenticationManagerBuilder in a class annotated with either @EnableWebSecurity
        auth.userDetailsService(authenticationService);
    }



	/*@Bean
	public FilterRegistrationBean myFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		return registration;
	}*/

	/*@Bean
	@ConditionalOnBean(name = DEFAULT_FILTER_NAME)
	public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration(
			SecurityProperties securityProperties) {
		DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
				DEFAULT_FILTER_NAME);
		registration.setOrder(securityProperties.getFilterOrder());
		registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR,
				DispatcherType.ASYNC);
		return registration;
	}*/
    }

