package cn.machaojin.domain.sonar.measure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 14:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasureItem {
    private String metric;
    private String value;
    private String component;
    private Boolean bestValue;
}
