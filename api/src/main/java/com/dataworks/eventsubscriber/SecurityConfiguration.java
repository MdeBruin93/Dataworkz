package com.dataworks.eventsubscriber;

import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final WebAuthDetailService webAuthDetailService;

    public SecurityConfiguration(WebAuthDetailService webAuthDetailService) {
        this.webAuthDetailService = webAuthDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/auth/register").permitAll()
                .antMatchers("/events/store").hasAnyRole("USER", "ADMIN")
                .antMatchers("/events/{id}/update").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/auth/my").authenticated()
                .antMatchers("/test/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/test/admin").hasRole("ADMIN")
//                .antMatchers("/post/create").authenticated()
                .and()
                .httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.webAuthDetailService);

        return daoAuthenticationProvider;
    }
}
