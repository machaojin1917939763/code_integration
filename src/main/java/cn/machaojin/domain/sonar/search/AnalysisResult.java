package cn.machaojin.domain.sonar.search;

import cn.machaojin.domain.sonar.search_projects.Paging;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:13
 */
@Data
@Builder
public class AnalysisResult {
    private Integer total;
    private Integer p;
    private Integer ps;
    private Paging paging;
    private Integer effortTotal;
    private List<Issues> issues;
}
