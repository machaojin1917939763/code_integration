package cn.machaojin.domain.sonar.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flow {
    private List<Location> locations;
}
