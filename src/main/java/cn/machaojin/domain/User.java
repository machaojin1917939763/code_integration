package cn.machaojin.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 16:34
 */
@Data
@Builder
public class User {
    private String userid;
    private String username;
    private List<String> roles;
    private List<String> buttons;
}
