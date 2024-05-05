package cn.machaojin.controller;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.domain.sonar.issue_snippets.CodeAnalysis;
import cn.machaojin.domain.sonar.issue_snippets.ComponentDetails;
import cn.machaojin.domain.sonar.search.AnalysisResult;
import cn.machaojin.domain.sonar.search_projects.SearchResult;
import cn.machaojin.feign.QualityGateClient;
import cn.machaojin.tool.ApiResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    @GetMapping("/components/search_projects")
    public ApiResult searchProject(){
        SearchResult searchResult = qualityGateClient.searchProjects(
                50,
                "reliability_rating%2Csecurity_rating%2Csecurity_review_rating%2Csqale_rating%2Ccoverage%2Cduplicated_lines_density%2Cncloc%2Calert_status%2Clanguages%2Ctags%2Cqualifier",
                "analysisDate%2CleakPeriodDate", "", "");
        return ApiResult.success(searchResult);
    }

    @CodeLog
    @JwtIgnore
    @GetMapping("/issues/search")
    public ApiResult issuesSearch(){
        AnalysisResult analysisResult = qualityGateClient.searchIssues(
                "cn.machaojin%3ACodeIntegration",
                "FILE_LINE",
                "CONFIRMED%2COPEN",
                100,
                1,
                "_all",
                "Asia%2FShanghai"
        );
        return ApiResult.success(analysisResult);
    }

    @CodeLog
    @JwtIgnore
    @GetMapping("/sources/issue_snippets")
    public ApiResult issueSnippets(){
        Map<String, ComponentDetails> stringComponentDetailsMap = qualityGateClient.issueSnippets(
                "f4faca4a-a622-430a-af08-59778b3756e1"
        );
        CodeAnalysis codeAnalysis = CodeAnalysis.builder().analysisDetails(stringComponentDetailsMap).build();
        return ApiResult.success(codeAnalysis);
    }

}
