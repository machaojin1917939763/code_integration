package cn.machaojin.service.websocket;

import cn.machaojin.config.SonarQubeConfig;
import cn.machaojin.domain.Issue;
import cn.machaojin.domain.Project;
import cn.machaojin.domain.sonar.search.AnalysisResult;
import cn.machaojin.domain.sonar.search.Issues;
import cn.machaojin.domain.sonar.search_projects.Component;
import cn.machaojin.domain.sonar.search_projects.SearchResult;
import cn.machaojin.feign.QualityGateClient;
import cn.machaojin.service.IssueService;
import cn.machaojin.service.ProjectService;
import cn.machaojin.tool.RedisUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 17:32
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProjectService {

    private final SonarQubeConfig sonarQubeConfig;
    private final QualityGateClient qualityGateClient;
    private final ProjectService projectService;
    private final IssueService issueService;
    private final RedisUtil redisUtil;


    public void createProject(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        Project project = JSONObject.parseObject(message.getPayload().toString(), Project.class);
        String fileName = sonarQubeConfig.getBasePackage();
        File file = new File(fileName);
        String commandLine = generatorCommandLine(project);
        //创建文件夹
        String path = getProjectAbsolutelyPath(fileName, project.getUrl());
        session.sendMessage(new TextMessage("创建文件夹"));
        session.sendMessage(new TextMessage("10"));
        if (!createFile(file)) {
            session.sendMessage(new TextMessage("文件夹存在！直接启动分析！"));
            session.sendMessage(new TextMessage("20"));
            startSonarQube(session, path, commandLine);
            session.close();
            return;
        }
        session.sendMessage(new TextMessage("创建文件夹成功"));

        session.sendMessage(new TextMessage("20"));
        session.sendMessage(new TextMessage("开始克隆仓库"));
        session.sendMessage(new TextMessage("30"));
        if (!carryOut(file, "git clone --progress " + project.getUrl(), session)) {
            session.sendMessage(new TextMessage("克隆仓库失败！请检查"));
            session.sendMessage(new TextMessage("exception"));
            session.close();
            return;
        }
        session.sendMessage(new TextMessage("克隆仓库成功"));
        session.sendMessage(new TextMessage("40"));

        if (!"Maven".equals(project.getType())) {
            session.sendMessage(new TextMessage("55"));
            session.sendMessage(new TextMessage("添加sonar-project.properties文件"));
            if (addProperties(path, project, session)) {
                session.sendMessage(new TextMessage("57"));
                session.sendMessage(new TextMessage("添加sonar-project.properties文件成功"));
            } else {
                session.sendMessage(new TextMessage("添加sonar-project.properties文件失败"));
                session.sendMessage(new TextMessage("exception"));
                session.close();
                return;
            }
        }
        startSonarQube(session, path, commandLine);
    }

    private String generatorCommandLine(Project project) {
        StringBuilder result = new StringBuilder();
        if ("Maven".equals(project.getType())) {
            result.append("mvn clean package sonar:sonar");

            // 生成不包含"-"的UUID
            String projectKey = UUID.randomUUID().toString().replace("-", "");
            LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Project::getName,project.getName());
            Project serviceOne = projectService.getOne(wrapper);
            if (serviceOne != null && serviceOne.getSonarKey() != null && !serviceOne.getSonarKey().isEmpty()) {
                projectKey = serviceOne.getSonarKey();
            }
            result.append(" -Dsonar.projectKey=").append(projectKey);

            // 添加项目名称
            String projectName = project.getName();
            result.append(" -Dsonar.projectName=").append(projectName);

            // 设置项目版本
            result.append(" -Dsonar.projectVersion=1.0");

            // 添加SonarQube登录令牌
            String sonarToken = sonarQubeConfig.getToken();
            result.append(" -Dsonar.login=").append(sonarToken);

            // 设置SonarQube的URL
            String sonarUrl = sonarQubeConfig.getBaseUrl();
            result.append(" -Dsonar.host.url=").append(sonarUrl);
        }
        return result.toString();
    }

    private Boolean addProperties(String path, Project project, WebSocketSession session) throws IOException {
        path = path + "\\sonar-project.properties";
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.println("sonar.projectKey=" + UUID.randomUUID().toString());
            writer.println("sonar.projectName=" + project.getName());
            writer.println("sonar.projectVersion=" + "1.0");
            writer.println("sonar.token=" + sonarQubeConfig.getToken());
            writer.println("sonar.host.url=" + sonarQubeConfig.getBaseUrl());
        } catch (FileNotFoundException e) {
            System.err.println("Error writing properties file: " + e.getMessage());
            e.printStackTrace();
            session.sendMessage(new TextMessage("exception"));
            session.close();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void startSonarQube(WebSocketSession session, String path, String commandLine) throws IOException {
        //分析
        session.sendMessage(new TextMessage("SonarQube开始分析"));
        session.sendMessage(new TextMessage("80"));
        if (sonarQube(path, session, commandLine)) {
            session.sendMessage(new TextMessage("SonarQube分析成功"));
            session.sendMessage(new TextMessage("90"));
        } else {
            session.sendMessage(new TextMessage("SonarQube分析失败"));
            session.sendMessage(new TextMessage("exception"));
            session.close();
            return;
        }
        session.sendMessage(new TextMessage("SonarQube分析结束"));
        session.sendMessage(new TextMessage("100"));
        session.close();
    }

    private Boolean sonarQube(String path, WebSocketSession session, String commandLine) {
        File file = new File(path);
        carryOut(file, commandLine, session);
        return Boolean.TRUE;
    }

    /**
     * 找出clone的最新的路径
     *
     * @param fileName
     * @param gitLink
     * @return
     */
    private String getProjectAbsolutelyPath(String fileName, String gitLink) {
        String[] split = gitLink.split("/");
        String[] split1 = split[split.length - 1].split("\\.");
        return fileName + "\\" + split1[0];
    }

    public Boolean createFile(File file) {
        if (file.exists()) {
            return Boolean.TRUE;
        }
        boolean mkdir = file.mkdir();
        if (!mkdir) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean carryOut(File directory, String command, WebSocketSession session) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // 设置工作目录
        processBuilder.directory(directory);
        // Windows平台通常使用cmd.exe
        processBuilder.command("cmd.exe", "/c", command);
        try {
            Process process = processBuilder.start();
            // 读取标准输出流
            Thread outputThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        session.sendMessage(new TextMessage(line));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // 读取错误流
            Thread errorThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        session.sendMessage(new TextMessage(line));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            outputThread.start();
            errorThread.start();
            // 等待线程结束
            outputThread.join();
            errorThread.join();
            // 等待命令执行结束
            int exitCode = process.waitFor();
            session.sendMessage(new TextMessage("\nExited with error code : " + exitCode));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void analyzeProject() {
        //获取所有的项目信息
        SearchResult searchResult = qualityGateClient.searchProjects(
                50,
                "reliability_rating%2Csecurity_rating%2Csecurity_review_rating%2Csqale_rating%2Ccoverage%2Cduplicated_lines_density%2Cncloc%2Calert_status%2Clanguages%2Ctags%2Cqualifier",
                "analysisDate%2CleakPeriodDate", "", "");
        for (Component component : searchResult.getComponents()) {
            LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Project::getName,component.getName());
            AnalysisResult analysisResult = qualityGateClient.searchIssues(
                    component.getKey(),
                    "FILE_LINE",
                    "CONFIRMED%2COPEN",
                    500,
                    1,
                    "_all",
                    "Asia%2FShanghai"
            );
            List<Issues> resultIssues = analysisResult.getIssues();
            //先删掉这个项目的所有问题
            issueService.remove(new LambdaQueryWrapper<>(Issue.class).eq(Issue::getProjectName,component.getName()));
            for (Issues resultIssue : resultIssues) {
                Issue issue = Issue
                        .builder()
                        .projectName(component.getName())
                        .issueCreator(resultIssue.getAuthor())
                        .issueType(resultIssue.getType())
                        .isResolved(resultIssue.getStatus())
                        .description(resultIssue.getMessage())
                        .score(getScore(resultIssue.getType()))
                        .issueKey(resultIssue.getKey())
                        .build();
                issueService.save(issue);
                redisUtil.set(resultIssue.getKey(),JSONObject.toJSONString(resultIssue));
            }
            projectService.update(Project.builder().sonarKey(component.getKey()).issueCount(analysisResult.getTotal()).build(),wrapper);
        }
    }
    private Integer getScore(String issueType){
        return switch (issueType) {
            case "CODE_SMELL" -> 10;
            case "BUG" -> 20;
            default -> 5;
        };
    }
}
