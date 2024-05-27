package cn.machaojin.domain.sonar.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 16:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityGate {
    private String key;
    private String name;
    private Boolean isDefault;
}
