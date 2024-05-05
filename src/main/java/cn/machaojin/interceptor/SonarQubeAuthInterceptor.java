package cn.machaojin.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 16:05
 */
@Data
public class SonarQubeAuthInterceptor implements RequestInterceptor {

    private String token;

    public SonarQubeAuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + token);
//        template.header("Content-Type", "application/json");
    }
}
