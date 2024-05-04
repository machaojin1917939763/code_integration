package cn.machaojin.domain.sonar;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:38
 */
@Data
@Builder
public class Facet {
    private String property;
    private List<Value> values;
}
