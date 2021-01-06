package com.dataworks.eventsubscriber;

import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auth/my").authenticated()
                .antMatchers(HttpMethod.POST, "/api/auth/forgot-password").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/reset-password").permitAll()
                .antMatchers(HttpMethod.GET, "/api/events").permitAll()
                .antMatchers(HttpMethod.POST, "/api/events").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/events/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/events/{id}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/events/{id}/subscribe").authenticated()
                .antMatchers(HttpMethod.GET, "/api/events/findbyuser").authenticated()
                .antMatchers(HttpMethod.POST, "/api/storage/upload").authenticated()
                .antMatchers(HttpMethod.POST, "/api/questions").authenticated()
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
