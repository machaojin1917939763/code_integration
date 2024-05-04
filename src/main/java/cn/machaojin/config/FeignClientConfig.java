package cn.machaojin.config;

import cn.machaojin.interceptor.SonarQubeAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 15:30
 */
@EnableFeignClients(basePackages = "cn.machaojin.feign")
@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    private final SonarQubeConfig sonarQubeConfig;

    @Bean
    public SonarQubeAuthInterceptor sonarQubeAuthInterceptor() {
        return new SonarQubeAuthInterceptor(sonarQubeConfig.getToken());
    }

}
