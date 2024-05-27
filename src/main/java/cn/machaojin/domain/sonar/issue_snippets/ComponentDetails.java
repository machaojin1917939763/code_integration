package cn.machaojin.domain.sonar.issue_snippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDetails {
    private Component component;
    private List<Source> sources;
}
