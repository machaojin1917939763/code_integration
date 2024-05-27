package cn.machaojin.domain.sonar.measure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 14:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Measures {
    private List<MeasureItem> measures;
}
