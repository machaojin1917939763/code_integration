package cn.machaojin.domain.sonar.search;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:15
 */
@Data
@Builder
public class Location {
    private String component;
    private TextRange textRange;
    private String msg;
}
