package cn.machaojin.domain.sonar.show;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ma Chaojin
 * @since 2024-05-07 16:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Impact {
    private String softwareQuality;
    private String severity;
}
