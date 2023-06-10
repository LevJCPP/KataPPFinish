package ru.levjcpp.katappfinish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.levjcpp.katappfinish.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final SuccessUserHandler successUserHandler;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService,
                             SuccessUserHandler successUserHandler,
                             PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity https) throws Exception {
        https.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "/admin/**").hasRole("ADMIN")
                .antMatchers("/auth/login", "/auth/register", "/error").permitAll()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .successHandler(successUserHandler)
                .failureUrl("/auth/login?error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
