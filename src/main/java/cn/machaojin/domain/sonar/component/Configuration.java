package cn.machaojin.domain.sonar.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 16:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    private Boolean showSettings;
    private Boolean showQualityProfiles;
    private Boolean showQualityGates;
    private Boolean showLinks;
    private Boolean showPermissions;
    private Boolean showHistory;
    private Boolean showUpdateKey;
    private Boolean showBackgroundTasks;
    private Boolean canApplyPermissionTemplate;
    private Boolean canBrowseProject;
    private Boolean canUpdateProjectVisibilityToPrivate;
    private List<String> extensions;
}
