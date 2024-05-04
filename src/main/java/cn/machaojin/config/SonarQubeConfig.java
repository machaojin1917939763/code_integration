package cn.machaojin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 15:25
 */
@Data
@Configuration
@ConfigurationProperties("sonar")
public class SonarQubeConfig {
    private String baseUrl;
    private String token;
}
