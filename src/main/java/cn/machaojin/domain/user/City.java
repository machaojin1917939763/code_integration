package cn.machaojin.domain.user;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-02 15:05
 */
@Data
@Builder
public class City {
    private String label;
    private String key;
}
