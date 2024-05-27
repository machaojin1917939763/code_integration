package cn.machaojin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 15:31
 */
@Component
@FeignClient(name = "testClient", url = "${sonar.baseUrl}")
public interface TestClient {

    @GetMapping("/api/qualitygates/show?name=Sonar%20way")
    public String getTest();
}
