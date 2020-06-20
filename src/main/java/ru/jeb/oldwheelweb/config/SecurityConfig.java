package ru.jeb.oldwheelweb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import ru.jeb.oldwheelweb.security.OldEntryPoint;

/**
 * @author Jeb
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;
    private final OldEntryPoint entryPoint;
    private final ApplicationConfig applicationConfig;

    public SecurityConfig(@Qualifier("wheelUserDetailsService") UserDetailsService userDetailsService, @Qualifier("cors") CorsConfigurationSource corsConfigurationSource, OldEntryPoint entryPoint, ApplicationConfig applicationConfig) {
        this.userDetailsService = userDetailsService;

        this.corsConfigurationSource = corsConfigurationSource;
        this.entryPoint = entryPoint;
        this.applicationConfig = applicationConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(corsConfigurationSource)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/account/login", "/account/register", "/account/restore", "/account/forgot", "/account/verification").permitAll()
                .antMatchers("/launcher/**", "/nsl/**").permitAll()
                .antMatchers("/event/**").permitAll()
                .antMatchers("/server/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .httpBasic()
                .authenticationEntryPoint(entryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
