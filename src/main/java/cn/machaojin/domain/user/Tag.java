package cn.machaojin.domain.user;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ma Chaojin
 * @since 2024-05-02 15:04
 */
@Data
@Builder
public class Tag {
    private String key;
    private String label;
}
