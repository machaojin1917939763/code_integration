package cn.machaojin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 15:35
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginTokenDto implements Serializable {
    private String token;
}
