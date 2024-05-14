package cn.machaojin.feign;

import cn.machaojin.domain.sonar.component.Project;
import cn.machaojin.domain.sonar.issue_snippets.ComponentDetails;
import cn.machaojin.domain.sonar.measure.Measures;
import cn.machaojin.domain.sonar.search.AnalysisResult;
import cn.machaojin.domain.sonar.search_projects.SearchResult;
import cn.machaojin.domain.sonar.show.RuleDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 19:36
 */
@Component
@FeignClient(name = "qualityGateClient", url = "${sonar.baseUrl}")
public interface QualityGateClient {

    @GetMapping("/api/components/search_projects")
    String qualityGate(
            @RequestParam(name = "project") String project,
            @RequestParam(name = "ps", required = false) Integer pageSize,
            @RequestParam(name = "facets") String facets,
            @RequestParam(name = "f") String fields,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "analysisDate", required = false) String analysisDate,
            @RequestParam(name = "leakPeriodDate", required = false) String leakPeriodDate,
            @RequestParam(name = "isFavorite", required = false) Boolean isFavorite
    );

    /**
     * 获取项目的问题数
     * @param projectKeys 项目的key，多个项目使用%2链接
     * @param metricKeys
     * @return
     */
    @GetMapping("/api/measures/search")
    String searchMeasures(
            @RequestParam(name = "projectKeys") String projectKeys,
            @RequestParam(name = "metricKeys") String metricKeys
    );

    @GetMapping("/api/metrics/search")
    String searchMetrics(@RequestParam(name = "ps", defaultValue = "500") int ps);

    @GetMapping("/api/projects/search_my_scannable_projects")
    String searchMyScannableProjects();

    /**
     * 获取项目的个数
     * @param pageSize
     * @param facets
     * @param fields
     * @param analysisDate
     * @param leakPeriodDate
     * @return
     */
    @GetMapping("/api/components/search_projects")
    SearchResult searchProjects(
            @RequestParam(name = "ps", defaultValue = "50") int pageSize,
            @RequestParam(name = "facets") String facets,
            @RequestParam(name = "f") String fields,
            @RequestParam(name = "analysisDate", required = false) String analysisDate,
            @RequestParam(name = "leakPeriodDate", required = false) String leakPeriodDate
    );

    @GetMapping("/api/ce/analysis_status")
    String analysisStatus(@RequestParam(name = "component") String component);

    @GetMapping("/api/measures/component")
    String componentMeasures(
            @RequestParam(name = "additionalFields") String additionalFields,
            @RequestParam(name = "component") String component,
            @RequestParam(name = "metricKeys") String metricKeys
    );

    @GetMapping("/api/navigation/component")
    String navigationComponent(@RequestParam(name = "component") String component);

    @GetMapping("/api/ce/component")
    String ceComponent(@RequestParam(name = "component") String component);

    @GetMapping("/api/measures/search_history")
    String searchMeasuresHistory(
            @RequestParam(name = "from") String fromDate,
            @RequestParam(name = "component") String component,
            @RequestParam(name = "metrics") String metrics,
            @RequestParam(name = "ps", defaultValue = "1000") int ps
    );

    @GetMapping("/api/qualitygates/show")
    String showQualityGates(@RequestParam(name = "name") String name);

    @GetMapping("/api/issues/search")
    AnalysisResult searchIssues(
            @RequestParam(name = "components") String components,
            @RequestParam(name = "s") String sort,
            @RequestParam(name = "issueStatuses") String issueStatuses,
            @RequestParam(name = "ps") int pageSize,
            @RequestParam(name = "p") int page,
            @RequestParam(name = "additionalFields") String additionalFields,
            @RequestParam(name = "timeZone") String timeZone
    );

    @GetMapping("/api/sources/issue_snippets")
    Map<String, ComponentDetails> issueSnippets(@RequestParam(name = "issueKey") String issueKey);

    @GetMapping("/api/rules/show")
    RuleDetail showRules(@RequestParam(name = "key") String key);

    @PostMapping("/api/issues/add_comment")
    String addComment(@RequestParam String issue,@RequestParam String text);

    @GetMapping("/api/measures/search")
    Measures getProjectDetail(@RequestParam String projectKeys, @RequestParam String metricKeys);

    @GetMapping("/api/navigation/component")
    Project getProjectIntroduce(@RequestParam String component);

}
