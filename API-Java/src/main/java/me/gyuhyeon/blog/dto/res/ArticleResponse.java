package me.gyuhyeon.blog.dto.res;

import lombok.*;
import me.gyuhyeon.blog.domain.Article;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse (Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
