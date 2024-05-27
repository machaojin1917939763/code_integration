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
public class Measures {
    private Double lines;
    private Double issues;
}
