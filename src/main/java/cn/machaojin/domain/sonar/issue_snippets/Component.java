package cn.machaojin.domain.sonar.issue_snippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Component {
    private String key;
    private String uuid;
    private String path;
    private String name;
    private String longName;
    private String q;
    private String project;
    private String projectName;
    private Measures measures;
}
