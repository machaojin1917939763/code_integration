package cn.machaojin.domain.sonar.show;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-07 16:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleDetail {
    private Rule rule;
    private List<Object> actives;
}
