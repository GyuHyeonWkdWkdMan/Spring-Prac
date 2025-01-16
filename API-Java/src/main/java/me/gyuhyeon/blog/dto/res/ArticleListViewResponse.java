package me.gyuhyeon.blog.dto.res;

import lombok.Getter;
import me.gyuhyeon.blog.domain.Article;


@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
