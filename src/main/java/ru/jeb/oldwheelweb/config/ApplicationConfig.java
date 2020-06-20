package ru.jeb.oldwheelweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeb
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "web")
public class ApplicationConfig {
    private int playerCount = 1;
    private String captchaSite = "";
    private String captchaSecret = "";
    private String apiKey = "";
}
