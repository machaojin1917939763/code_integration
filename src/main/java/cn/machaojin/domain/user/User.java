package cn.machaojin.domain.user;

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
    private String name;
    private String avatar;
    private String userid;
    private String email;
    private String signature;
    private String title;
    private String group;
    private List<Tag> tags;
    private Integer notifyCount;
    private Integer unreadCount;
    private String country;
    private String access;
    private Geographic geographic;
    private String address;
    private String phone;

}
