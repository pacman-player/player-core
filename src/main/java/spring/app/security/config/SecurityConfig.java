package spring.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import spring.app.security.handlers.CustomAuthenticationFailureHandler;
import spring.app.security.handlers.CustomAuthenticationSuccessHandler;
import spring.app.security.service.UserDetailsServiceImpl;

@Configuration
@ComponentScan("spring.app")
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl authenticationService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl authenticationService,
                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.authenticationService = authenticationService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Отключим проверку CSRF для подключений нашего бота к серверу.
                .csrf().ignoringAntMatchers("/api/tlg/**")
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/registration/**",
                        "/api/registration/**",
                        "/login-captcha",
                        "/notFound",
                        "/translation",
                        "/google",
                        "/googleAuth",
                        "/player",
                        "/vkAuth",
                        "/vkontakte",
                        "/css/style.css",
                        "/js/security.js",
                        "/js/reg-check.js",
                        "/js/registrationFirstPage.js")
                .permitAll()
                .antMatchers("/notification",
                        "/fragment",
                        "/topic",
                        "/app")
                .authenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("USER")
                .antMatchers("/api/tlg/**").hasAuthority("BOT")
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/processing-url")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // The name of the configureGlobal method is not important. However,
        // it is important to only configure AuthenticationManagerBuilder in a class annotated with either @EnableWebSecurity
        auth.userDetailsService(authenticationService).passwordEncoder(getPasswordEncoder());
    }
}