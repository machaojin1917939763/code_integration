package cn.machaojin.domain.sonar.search;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:16
 */
@Data
@Builder
public class Impact {
    private String softwareQuality;
    private String severity;
}
