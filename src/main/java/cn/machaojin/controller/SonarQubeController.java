package cn.machaojin.controller;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.domain.sonar.SearchResult;
import cn.machaojin.feign.QualityGateClient;
import cn.machaojin.tool.ApiResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:41
 */
@Data
@RestController
@RequestMapping("sonar")
@RequiredArgsConstructor
public class SonarQubeController {
    private final QualityGateClient qualityGateClient;


    @CodeLog
    @JwtIgnore
    @GetMapping("/searchProject")
    public ApiResult searchProject(){
        SearchResult searchResult = qualityGateClient.searchProjects(
                50,
                "reliability_rating%2Csecurity_rating%2Csecurity_review_rating%2Csqale_rating%2Ccoverage%2Cduplicated_lines_density%2Cncloc%2Calert_status%2Clanguages%2Ctags%2Cqualifier",
                "analysisDate%2CleakPeriodDate", "", "");
        return ApiResult.success(searchResult);
    }

}
