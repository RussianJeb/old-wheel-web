package ru.jeb.oldwheelweb.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.jeb.oldwheelweb.security.filter.ServerApiKeyFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jeb
 */
@Configuration
public class WebConfig {
    private final ServerApiKeyFilter serverApiKeyFilter;

    public WebConfig(ServerApiKeyFilter serverApiKeyFilter) {
        this.serverApiKeyFilter = serverApiKeyFilter;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("cors")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<ServerApiKeyFilter> headerValidatorFilter() {
        FilterRegistrationBean<ServerApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(serverApiKeyFilter);
        registrationBean.setEnabled(true);
        registrationBean.addUrlPatterns("/server/*");
        return registrationBean;
    }

}
