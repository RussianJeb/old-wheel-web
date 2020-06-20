package ru.jeb.oldwheelweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeb
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "launcher")
public class LauncherConfig {
    private String apiKey;
}
