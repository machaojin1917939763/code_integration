package cn.machaojin.domain.sonar;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:38
 */
@Data
@Builder
public class Paging {
    private Integer pageIndex;
    private Integer pageSize;
    private Integer total;

}
