package cn.machaojin.domain.sonar.issue_snippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Source {
    private Integer line;
    private String code;
    private String scmRevision;
    private String scmAuthor;
    private String scmDate;
    private Boolean duplicated;
    private Boolean isNew;
}
