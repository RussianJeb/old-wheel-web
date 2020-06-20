package ru.jeb.oldwheelweb.security.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.jeb.oldwheelweb.config.ApplicationConfig;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jeb
 */
@Component
public class ServerApiKeyFilter extends OncePerRequestFilter {
    private final ApplicationConfig applicationConfig;

    public ServerApiKeyFilter(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("Wheel-API-Key");
        if (!applicationConfig.getApiKey().equals(apiKey)) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Not found");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
