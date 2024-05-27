package cn.machaojin.controller;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.domain.sonar.component.Project;
import cn.machaojin.domain.sonar.issue_snippets.ComponentDetails;
import cn.machaojin.domain.sonar.measure.Measures;
import cn.machaojin.domain.sonar.search.AnalysisResult;
import cn.machaojin.domain.sonar.search.Comment;
import cn.machaojin.domain.sonar.search.Issues;
import cn.machaojin.domain.sonar.search_projects.Component;
import cn.machaojin.domain.sonar.search_projects.SearchResult;
import cn.machaojin.domain.sonar.show.RuleDetail;
import cn.machaojin.dto.CommentDto;
import cn.machaojin.feign.QualityGateClient;
import cn.machaojin.service.ProjectService;
import cn.machaojin.service.websocket.CreateProjectService;
import cn.machaojin.tool.ApiResult;
import cn.machaojin.tool.RedisUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static cn.machaojin.constants.SonarConstant.metricKeys;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:41
 */
@Slf4j
@Data
@RestController
@RequestMapping("sonar")
@RequiredArgsConstructor
public class SonarQubeController {
    private final QualityGateClient qualityGateClient;
    private final RedisUtil redisUtil;
    private final CreateProjectService createProjectService;
    private final ProjectService projectService;


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
    public ApiResult issuesSearch(@RequestBody String projectKey){
        AnalysisResult analysisResult = qualityGateClient.searchIssues(
                projectKey,
                "FILE_LINE",
                "CONFIRMED%2COPEN",
                500,
                1,
                "_all",
                "Asia%2FShanghai"
        );
        return ApiResult.success(analysisResult);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/sources/issue_snippets")
    public ApiResult issueSnippets(@RequestBody String issueKey){
        Map<String, ComponentDetails> stringComponentDetailsMap = qualityGateClient.issueSnippets(
                issueKey.trim().replace("\"","")
        );
        ComponentDetails componentDetails = new ComponentDetails();
        for (Map. Entry<String, ComponentDetails> entry : stringComponentDetailsMap. entrySet()){
            componentDetails = entry.getValue();
        }
        return ApiResult.success(componentDetails);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/sources/issue_redis")
    public ApiResult getIssueForRedis(@RequestBody String issueKey){
        Object object = redisUtil.get(issueKey.trim().replace("\"",""));
        return ApiResult.success(object);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/sources/rule")
    public ApiResult getRule(@RequestBody String ruleKey){
        RuleDetail ruleDetail = qualityGateClient.showRules(ruleKey.trim().replace("\"", ""));
        return ApiResult.success(ruleDetail);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/sources/add_comment")
    public ApiResult postNewComment(@RequestBody CommentDto commentDto){
        commentDto.setIssue(commentDto.getIssue().trim().replace("\"", ""));
        commentDto.setText(commentDto.getText().trim().replace("\"", ""));
        log.info(commentDto.toString());
        String comment = qualityGateClient.addComment(commentDto.getIssue(), commentDto.getText());
        JSONObject jsonObject = JSON.parseObject(comment);
        // 获取issue对象
        JSONObject issue = jsonObject.getJSONObject("issue");
        // 从issue对象中获取comments数组
        JSONArray comments = issue.getJSONArray("comments");
        List<Comment> list = new ArrayList<>();
        for (Object object : comments) {
            list.add(((JSONObject) object).toJavaObject(Comment.class));
        }
        String key = (String) issue.get("key");
        String issuesJson = (String) redisUtil.get(key);
        Issues issues = JSON.parseObject(issuesJson, Issues.class);
        list.sort(Comparator.comparing(Comment::getCreatedAt).reversed());
        issues.setComments(list);
        redisUtil.set((String) issue.get("key"), JSON.toJSONString(issues));
        return ApiResult.success(comment);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/measure/get_project_detail")
    public ApiResult getProjectDetail(@RequestBody String projectKey){
        Measures projectDetail = qualityGateClient.getProjectDetail(
                projectKey.trim().replace("\"", "")
                , metricKeys);
        return ApiResult.success(projectDetail);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/navigation/get_project_introduce")
    public ApiResult getProjectIntroduce(@RequestBody String projectKey){
        Project projectIntroduce = qualityGateClient.getProjectIntroduce(
                projectKey.trim().replace("\"", ""));
        return ApiResult.success(projectIntroduce);
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/issue/refresh_issue")
    public ApiResult refreshIssue(@RequestBody String projectKey){
        projectKey = projectKey.trim().replace("\"","");
        cn.machaojin.domain.Project project = projectService.getOne(new LambdaQueryWrapper<>(cn.machaojin.domain.Project.class).eq(cn.machaojin.domain.Project::getSonarKey, projectKey));
        createProjectService.getAnalysisResult(Component.builder().key(project.getSonarKey()).name(project.getName()).build());
        return ApiResult.success();
    }

}
