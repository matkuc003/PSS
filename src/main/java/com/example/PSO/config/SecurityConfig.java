package com.example.PSO.config;

import com.example.PSO.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests().antMatchers("/api/user/register").permitAll()
                .and().authorizeRequests().antMatchers("/api/user/all").hasRole("ADMIN")
                .and().authorizeRequests().antMatchers("/api/user/byRole/**").hasRole("ADMIN")
                .and().authorizeRequests().antMatchers("/api/user/**").authenticated()
                .and().authorizeRequests().antMatchers("/api/delegation/**").authenticated()
                .and().authorizeRequests().antMatchers("/panel").authenticated()
                .and().authorizeRequests().antMatchers("/register").permitAll()
                .and().formLogin().defaultSuccessUrl("/panel").permitAll()
                .and().logout().logoutSuccessUrl("/login");

        http.authorizeRequests().anyRequest().authenticated().and().oauth2Login()
                .defaultSuccessUrl("/oauth/loginSuccess")
                .failureUrl("/login");
    }

}
