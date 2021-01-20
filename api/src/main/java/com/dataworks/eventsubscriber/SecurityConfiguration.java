package com.dataworks.eventsubscriber;

import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final WebAuthDetailService webAuthDetailService;
    private final BasicAuthenticationEntryPoint basicAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auth/my").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/auth/my").authenticated()
                .antMatchers(HttpMethod.POST, "/api/auth/forgot-password").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/reset-password").permitAll()
                .antMatchers(HttpMethod.GET, "/api/events").permitAll()
                .antMatchers(HttpMethod.POST, "/api/events").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/events/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/events/{id}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/events/{id}/subscribe").authenticated()
                .antMatchers(HttpMethod.GET, "/api/events/findbyuser").authenticated()
                .antMatchers(HttpMethod.POST, "/api/storage/upload").authenticated()
                .antMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .antMatchers(HttpMethod.GET, "/api/categories/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/questions").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/questions/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/questions/{id}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/answers").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/answers/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/answers/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/blocked").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/subscriptions").authenticated()
//                .antMatchers("/post/create").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint);

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
