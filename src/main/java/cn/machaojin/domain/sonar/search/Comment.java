package cn.machaojin.domain.sonar.search;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 14:16
 */
@Data
@Builder
public class Comment {
    private String key;
    private String login;
    private String htmlText;
    private String markdown;
    private Boolean updatable;
    private Date createdAt;
}
