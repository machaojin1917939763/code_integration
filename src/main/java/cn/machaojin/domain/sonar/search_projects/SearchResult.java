package cn.machaojin.domain.sonar.search_projects;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-03 20:34
 */
@Data
@Builder
public class SearchResult {
    private Paging paging;
    private List<Component> components;
    private List<Facet> facets;
}
