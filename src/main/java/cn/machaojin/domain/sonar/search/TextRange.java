package cn.machaojin.domain.sonar.search;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:14
 */
@Data
@Builder
public class TextRange {
    private Integer startLine;
    private Integer endLine;
    private Integer startOffset;
    private Integer endOffset;
}
