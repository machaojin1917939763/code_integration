package cn.machaojin.domain.sonar.search_projects;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:38
 */
@Data
@Builder
public class Component {
    private String key;
    private String name;
    private String qualifier;
    private Boolean isFavorite;
    private String analysisDate;
    private List<String> tags;
    private String visibility;

}
