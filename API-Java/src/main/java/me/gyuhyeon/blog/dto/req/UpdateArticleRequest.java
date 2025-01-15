package me.gyuhyeon.blog.dto.req;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
}
