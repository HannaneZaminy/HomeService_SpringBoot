package ir.homeService.configuration;

import ir.homeService.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationSuccessHandler successHandler;

    public UserSecurityConfig(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, AuthenticationSuccessHandler successHandler) {
        super();
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/userLogin", "/customer/register", "/expert/register","/loginFailed","/static/**",
                        "/customer/verify/**","/expert/verify/**","/managerLogout")
                .permitAll()
                .anyRequest()
                .hasAnyRole("CUSTOMER","EXPERT")

                .and()
                .formLogin()
                .loginPage("/userLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureHandler((request, response, exception) -> {
                    String email = request.getParameter("email");
                    String error = exception.getMessage();
                    System.out.println("A failed login attempt with email: "
                            + email + ". Reason: " + error);
                    String redirectUrl = request.getContextPath() + "/loginFailed";
                    response.sendRedirect(redirectUrl);
                })

                .loginProcessingUrl("/userLogin")

                .and()
                .logout()
                .logoutUrl("/userLogout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDeniedPage.jsp");

    }


}

