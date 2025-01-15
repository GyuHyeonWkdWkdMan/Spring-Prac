package me.gyuhyeon.blog.controller;

import lombok.*;
import me.gyuhyeon.blog.domain.Article;
import me.gyuhyeon.blog.dto.req.*;
import me.gyuhyeon.blog.dto.res.ArticleResponse;
import me.gyuhyeon.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class BlogAPIController {

    private final BlogService blogService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("api/articles/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) { // URL의 id값의 해당하는 값을 파라미터로 받음
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
        .body(new ArticleResponse(article));
    }

    @DeleteMapping("api/articles/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {  // URL의 id값의 해당하는 값을 파라미터로 받음
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
