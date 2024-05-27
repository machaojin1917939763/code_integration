package cn.machaojin.domain.sonar.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 16:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String key;
    private String id;
    private String name;
    private String description;
    private Boolean isFavorite;
    private String branch;
    private String visibility;
    private List<String> extensions;
    private String version;
    private String analysisDate;
    private List<QualityProfile> qualityProfiles;
    private QualityGate qualityGate;
    private Configuration configuration;
    private List<Breadcrumb> breadcrumbs;
}
