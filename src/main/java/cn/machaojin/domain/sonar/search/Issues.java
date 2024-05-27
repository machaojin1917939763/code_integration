package cn.machaojin.domain.sonar.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:14
 */
@Data
@Builder
public class Issues {
    private String key;
    private String rule;
    private String severity;
    private String component;
    private String project;
    private Integer line;
    private String hash;
    private TextRange textRange;
    private List<Flow> flows;
    private String status;
    private String message;
    private String effort;
    private String debt;
    private String assignee;
    private String author;
    private List<String> tags;
    private List<String> transitions;
    private List<String> actions;
    private List<Comment> comments;
    private String creationDate;
    private String updateDate;
    private String type;
    private String scope;
    private Boolean quickFixAvailable;
    private List<Impact> impacts;
}
