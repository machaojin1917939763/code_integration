package cn.machaojin.domain.sonar.search_projects;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:37
 */
@Data
@Builder
public class Value {
    private String val;
    private Integer count;

}