package ir.homeService.configuration;

import ir.homeService.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Order(1)
public class ManagerSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    public ManagerSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        super();
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zahra")
                .password(passwordEncoder.encode("Za1996@#"))
                .roles("MANAGER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/managerPage/**","/service/**","/subService/addNewSubService","/user/**","/managerRestController/**")

                .and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/", "/managerPage", "/static/**","/dist/**","/managerLogout")
                .permitAll()
                .anyRequest().hasRole("MANAGER")

                .and()
                .formLogin()
                .loginPage("/managerPage")
                .usernameParameter("userName")
                .passwordParameter("password")
                .successForwardUrl("/managerPage/login")
                .loginProcessingUrl("/managerPage/login")

                .and()
                .logout()
                .logoutUrl("/managerLogout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedPage("/alert/accessDeniedPage.jsp");


    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

}

