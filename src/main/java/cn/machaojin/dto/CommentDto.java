package cn.machaojin.dto;

import lombok.*;

/**
 * @author Ma Chaojin
 * @since 2024-05-07 20:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String issue;
    private String text;
}
